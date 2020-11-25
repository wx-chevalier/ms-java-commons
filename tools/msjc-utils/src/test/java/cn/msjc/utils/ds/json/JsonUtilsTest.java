package cn.msjc.utils.ds.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import org.junit.jupiter.api.Test;

class JsonUtilsTest {

  private final JsonUtils JSON = JsonUtils.JSON;

  @Test
  void testNewNodeWorks() {
    ObjectNode o1 = JSON.newObjectNode();
    assertEquals("{}", JSON.toString(o1));

    ArrayNode a1 = JSON.newArrayNode();
    assertEquals("[]", JSON.toString(a1));

    a1.add(o1);
    assertEquals("[{}]", JSON.toString(a1));

    a1.add(JSON.newObjectNode(n -> n.put("hello", "world")));
    assertEquals("[{},{\"hello\":\"world\"}]", JSON.toString(a1));

    a1.add(JSON.newArrayNode(n -> n.add(42)));
    assertEquals("[{},{\"hello\":\"world\"},[42]]", JSON.toString(a1));
  }

  @Test
  void testNewJsonString() {
    assertEquals("{}", JSON.newJsonObjectString(n -> {}));
    assertEquals("{\"hello\":\"world\"}", JSON.newJsonObjectString(n -> n.put("hello", "world")));

    assertEquals("[]", JSON.newJsonArrayString(n -> {}));
    assertEquals("[42,\"hello\"]", JSON.newJsonArrayString(n -> n.add(42).add("hello")));
  }

  @Test
  void testNullCheck() {
    assertTrue(JSON.isNull(null));
    assertTrue(JSON.isNull(JSON.fromString("null")));
  }

  @Test
  void testToJsonNode() {
    M m = new M().setM("hello");
    JsonNode mNode = JSON.toJsonNode(m);
    M m1 = JSON.fromJsonNode(mNode, M.class);
    assertEquals(m, m1);
  }

  @SuppressWarnings("ConstantConditions")
  @Test
  void testFromString() {
    M m1 = JSON.fromString("{\"m\":\"hello\"}", M.class);
    assertEquals(new M().setM("hello"), m1);

    M m2 = JSON.fromString("null", M.class);
    assertNull(m2);

    M m3 = JSON.fromString(null, M.class);
    assertNull(m3);

    List<M> ms1 = JSON.fromString("[{\"m\":\"hello\"}]", new TypeReference<List<M>>() {});
    assertEquals(Collections.singletonList(new M().setM("hello")), ms1);

    List<M> ms2 = JSON.fromString("null", new TypeReference<List<M>>() {});
    assertNull(ms2);

    List<M> ms3 = JSON.fromString(null, new TypeReference<List<M>>() {});
    assertNull(ms3);
  }

  @Test
  void testFromNullReturnsNull() {
    assertNull(JSON.fromBytes(null));
    assertNull(JSON.fromBytesOrGet(null, JSON::newObjectNode));
    assertNull(JSON.fromBytes(null, JSON.newObjectNode()));

    assertNull(JSON.fromString(null));
    assertNull(JSON.fromStringOrGet(null, JSON::newObjectNode));
    assertNull(JSON.fromString(null, JSON.newObjectNode()));

    assertNull(JSON.fromJsonNode(null, String.class));
    assertNull(JSON.fromJsonNodeOrGet(null, String.class, () -> "42"));
    assertNull(JSON.fromJsonNode(null, String.class, "42"));

    assertNull(JSON.fromJsonNode(null, new TypeReference<List<String>>() {}));
    assertNull(
        JSON.fromJsonNodeOrGet(null, new TypeReference<List<String>>() {}, Collections::emptyList));
    assertNull(
        JSON.fromJsonNode(null, new TypeReference<List<String>>() {}, Collections.emptyList()));
  }

  @Test
  void testFromXXXThrows() {
    String errJsonString = "this is not a json string";
    byte[] errJsonBytes = errJsonString.getBytes(StandardCharsets.UTF_8);

    ArrayNode notMNode = JSON.newArrayNode();
    notMNode.add("this is not M class content");

    assertThrows(UncheckedIOException.class, () -> JSON.fromString(errJsonString, JsonNode.class));
    assertThrows(IllegalArgumentException.class, () -> JSON.fromJsonNode(notMNode, M.class));
    assertThrows(UncheckedIOException.class, () -> JSON.fromBytes(errJsonBytes));
  }

  @Test
  void testWithDefaultIgnoresError() {
    String errJsonString = "this is not a json string";
    byte[] errJsonBytes = errJsonString.getBytes(StandardCharsets.UTF_8);

    ObjectNode defaultNode = JSON.newObjectNode();

    ArrayNode notMNode = JSON.newArrayNode();
    notMNode.add("this is not M class content");
    M defaultM = new M();

    assertEquals(defaultNode, JSON.fromBytesOrGet(errJsonBytes, () -> defaultNode));
    assertEquals(defaultNode, JSON.fromBytes(errJsonBytes, defaultNode));

    assertEquals(defaultNode, JSON.fromStringOrGet(errJsonString, () -> defaultNode));
    assertEquals(defaultNode, JSON.fromString(errJsonString, defaultNode));

    assertEquals(defaultM, JSON.fromJsonNodeOrGet(notMNode, M.class, () -> defaultM));
    assertEquals(defaultM, JSON.fromJsonNode(notMNode, M.class, defaultM));

    assertEquals(
        Collections.emptyList(),
        JSON.fromJsonNodeOrGet(notMNode, new TypeReference<List<M>>() {}, Collections::emptyList));
    assertEquals(
        Collections.emptyList(),
        JSON.fromJsonNode(notMNode, new TypeReference<List<M>>() {}, Collections.emptyList()));
  }

  @Test
  void test_accessors() {
    ObjectNode obj = JSON.newObjectNode(n -> n.putPOJO("hello", new M().setM("world")));
    assertTrue(JSON.isNull(obj, "a"));
    assertTrue(JSON.isNull(obj, "hello", "a"));
    assertFalse(JSON.isNull(obj, "hello", "m"));
    assertEquals("world", JSON.getString(obj, "hello", "m").getOrNull());
  }

  @Test
  void testVavrModule() {
    io.vavr.collection.List<String> s =
        JSON.fromString("[\"a\"]", new TypeReference<io.vavr.collection.List<String>>() {});
    assertNotNull(s);
    assertEquals(1, s.size());
    assertEquals("a", s.head());
  }

  @Data
  static class M {
    private String m;
  }
}
