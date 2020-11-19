package cn.msjc.utils.ds.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.vavr.API;
import io.vavr.collection.Iterator;
import io.vavr.control.Option;
import io.vavr.jackson.datatype.VavrModule;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Nullable;

/**
 * JSON 工具，对 Jackson ObjectMapper 的浅封装
 *
 * <ul>
 *   <li>toXXX 转换为 JSON 对象/JSON 字符串；可以定制 null 对象返回的字符串，默认返回 null 值序列化的结果，也即 "null"
 *   <li>fromXXX 从 JSON 字符串/JSON 对象转换回指定类型，转换异常抛出
 *   <li>fromXXX(..., defaultValue) 同 fromXXX，如果异常，则返回 defaultValue
 *   <li>fromXXXOrGet(..., defaultValueGetter) 同 fromXXX，如果异常，计算 defaultValueGetter 返回
 *   <li>- from 系列函数，输入 null 返回 null
 * </ul>
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonTools {

  private final ObjectMapper OM;

  public static final JsonTools JSON = newJsonTools(new ObjectMapper());

  public static JsonTools newJsonTools(ObjectMapper mapper) {
    mapper.registerModule(new VavrModule());
    return new JsonTools(mapper);
  }

  public static JsonTools newJsonTools(Consumer<ObjectMapper> mapperCustomizer) {
    ObjectMapper mapper = new ObjectMapper();
    mapperCustomizer.accept(mapper);
    return new JsonTools(mapper);
  }

  public ObjectNode newObjectNode() {
    return OM.createObjectNode();
  }

  public ObjectNode newObjectNode(Consumer<ObjectNode> nodeConsumer) {
    ObjectNode objectNode = OM.createObjectNode();
    nodeConsumer.accept(objectNode);
    return objectNode;
  }

  public ArrayNode newArrayNode() {
    return OM.createArrayNode();
  }

  public ArrayNode newArrayNode(Consumer<ArrayNode> nodeConsumer) {
    ArrayNode arrayNode = OM.createArrayNode();
    nodeConsumer.accept(arrayNode);
    return arrayNode;
  }

  public String newJsonObjectString(Consumer<ObjectNode> nodeConsumer) {
    ObjectNode node = newObjectNode();
    nodeConsumer.accept(node);
    return toString(node);
  }

  public String newJsonArrayString(Consumer<ArrayNode> nodeConsumer) {
    ArrayNode node = newArrayNode();
    nodeConsumer.accept(node);
    return toString(node);
  }

  // accessors

  public <T> Option<T> get(JsonNode node, Function<JsonNode, T> converter, String... paths) {
    for (String path : paths) {
      if (isNull(node)) {
        break;
      } else {
        if (node.isPojo()) {
          // handing POJO
          node = toJsonNode(node);
          node = node.get(path);
        } else {
          node = node.get(path);
        }
      }
    }
    if (isNull(node)) {
      return API.None();
    } else {
      return API.Option(converter.apply(node));
    }
  }

  public boolean isNull(JsonNode node) {
    return node == null || node.isNull();
  }

  public boolean isNull(JsonNode node, String... paths) {
    return get(node, this::isNull, paths).getOrElse(true);
  }

  public Option<Long> getLong(JsonNode node, String... paths) {
    return get(node, JsonNode::asLong, paths);
  }

  public Option<Integer> getInt(JsonNode node, String... paths) {
    return get(node, JsonNode::asInt, paths);
  }

  public Option<Double> getDouble(JsonNode node, String... paths) {
    return get(node, JsonNode::asDouble, paths);
  }

  public Option<String> getString(JsonNode node, String... paths) {
    return get(node, JsonNode::asText, paths);
  }

  public JsonNode removeNull(JsonNode node) {
    if (node instanceof ObjectNode) {
      for (String f : Iterator.ofAll(node.fieldNames()).toList()) {
        JsonNode subNode = node.get(f);
        if (subNode.isNull()) {
          ((ObjectNode) node).remove(f);
        }
      }
    }
    return node;
  }

  // converters

  public JsonNode toJsonNode(Object object) {
    return OM.valueToTree(object);
  }

  // node == null -> null
  @Nullable
  public <T> T fromJsonNode(@Nullable JsonNode node, Class<T> clazz) {
    return OM.convertValue(node, clazz);
  }

  // ignore error and calculate default value
  @Nullable
  public <T> T fromJsonNodeOrGet(
      @Nullable JsonNode node, Class<T> clazz, Supplier<T> defaultValGetter) {
    try {
      return fromJsonNode(node, clazz);
    } catch (Throwable t) {
      return defaultValGetter.get();
    }
  }

  // ignore error and return default value
  @Nullable
  public <T> T fromJsonNode(@Nullable JsonNode node, Class<T> clazz, T defaultValue) {
    return fromJsonNodeOrGet(node, clazz, () -> defaultValue);
  }

  // node == null -> null
  @Nullable
  public <T> T fromJsonNode(@Nullable JsonNode node, TypeReference<T> typeReference) {
    return OM.convertValue(node, typeReference);
  }

  // ignore error and calculate default value
  @Nullable
  public <T> T fromJsonNodeOrGet(
      @Nullable JsonNode node, TypeReference<T> typeReference, Supplier<T> defaultValGetter) {
    try {
      return fromJsonNode(node, typeReference);
    } catch (Throwable t) {
      return defaultValGetter.get();
    }
  }

  // ignore error and return default value
  @Nullable
  public <T> T fromJsonNode(
      @Nullable JsonNode node, TypeReference<T> typeReference, T defaultValue) {
    return fromJsonNodeOrGet(node, typeReference, () -> defaultValue);
  }

  @Nullable
  public JsonNode fromBytes(@Nullable byte[] bytes) {
    if (bytes == null) {
      return null;
    }
    try {
      return OM.readTree(bytes);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public JsonNode fromBytesOrGet(byte[] bytes, Supplier<JsonNode> defaultValueGetter) {
    try {
      return fromBytes(bytes);
    } catch (Throwable t) {
      return defaultValueGetter.get();
    }
  }

  public JsonNode fromBytes(byte[] bytes, JsonNode defaultValue) {
    return fromBytesOrGet(bytes, () -> defaultValue);
  }

  public JsonNode fromString(String data) {
    if (data == null) {
      return null;
    }
    try {
      return OM.readTree(data);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public JsonNode fromStringOrGet(String data, Supplier<JsonNode> defaultValueGetter) {
    try {
      return fromString(data);
    } catch (Throwable t) {
      return defaultValueGetter.get();
    }
  }

  public JsonNode fromString(String data, JsonNode defaultValue) {
    return fromStringOrGet(data, () -> defaultValue);
  }

  @Nullable
  public <V> V fromString(String json, TypeReference<V> typeReference) {
    if (json == null) {
      return null;
    }
    try {
      return OM.readValue(json, typeReference);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  @Nullable
  public <V> V fromString(String json, Class<V> clazz) {
    if (json == null) {
      return null;
    }
    try {
      return OM.readValue(json, clazz);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public <V> V fromString(String json, TypeReference<V> typeReference, V defaultValue) {
    return fromStringOrGet(json, typeReference, () -> defaultValue);
  }

  public <V> V fromString(String json, Class<V> clazz, V defaultValue) {
    return fromStringOrGet(json, clazz, () -> defaultValue);
  }

  public <V> V fromStringOrGet(
      String json, TypeReference<V> typeReference, Supplier<V> defaultValueGetter) {
    try {
      return OM.readValue(json, typeReference);
    } catch (Exception e) {
      return defaultValueGetter.get();
    }
  }

  public <V> V fromStringOrGet(String json, Class<V> clazz, Supplier<V> defaultValueGetter) {
    try {
      return OM.readValue(json, clazz);
    } catch (Exception e) {
      return defaultValueGetter.get();
    }
  }

  @Nullable
  public <V> V fromInputStream(InputStream inputStream, TypeReference<V> typeReference) {
    if (inputStream == null) {
      return null;
    }
    try {
      return OM.readValue(inputStream, typeReference);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public String toStringOrGetNullString(Object object, Supplier<String> nullStringGetter) {
    if (object == null) {
      return nullStringGetter.get();
    }
    return toString(object);
  }

  public String toString(Object object, String nullString) {
    return toStringOrGetNullString(object, () -> nullString);
  }

  public String toString(Object object) {
    try {
      return OM.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new UncheckedIOException(e);
    }
  }

  public String toString(JsonNode node) {
    try {
      return OM.writeValueAsString(node);
    } catch (JsonProcessingException e) {
      throw new UncheckedIOException(e);
    }
  }

  public byte[] toBytes(JsonNode node) {
    try {
      return OM.writeValueAsBytes(node);
    } catch (JsonProcessingException e) {
      throw new UncheckedIOException(e);
    }
  }
}
