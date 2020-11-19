package com.unionfab.infrastructure.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.unionfab.domain.EntityId;
import io.vavr.API;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;

@Slf4j
public class MyBatisTools {

  /**
   * 使用 mapper 获取给定条件数据的数目
   *
   * @param mapper MyBatis mapper
   * @param cond 其中可以定制查询条件，如可以传递 {@code w -> { w.eq(T::getId, 1); }}
   * @param <T> Map 的对象
   * @return 查询结果
   */
  public static <T> int selectCount(BaseMapper<T> mapper, Consumer<LambdaQueryWrapper<T>> cond) {
    LambdaQueryWrapper<T> w = new LambdaQueryWrapper<>();
    cond.accept(w);
    return mapper.selectCount(w);
  }

  /**
   * 使用 mapper 判断给定条件的数据是否存在
   *
   * @param mapper MyBatis mapper
   * @param cond 其中可以定制查询条件，如可以传递 {@code w -> { w.eq(T::getId, 1); }}
   * @param <T> Map 的对象
   * @return 查询结果
   */
  public static <T> boolean exists(BaseMapper<T> mapper, Consumer<LambdaQueryWrapper<T>> cond) {
    LambdaQueryWrapper<T> w = new LambdaQueryWrapper<>();
    cond.accept(w);
    return mapper.selectCount(w) != 0;
  }

  /**
   * 使用 mapper 根据给定条件查询一个结果
   *
   * @param mapper MyBatis mapper
   * @param cond 其中可以定制查询条件，如可以传递 {@code w -> { w.eq(T::getId, 1); }}
   * @param <T> Map 的对象
   * @return 查询结果
   */
  public static <T> Option<T> selectOne(
      BaseMapper<T> mapper, Consumer<LambdaQueryWrapper<T>> cond) {
    LambdaQueryWrapper<T> w = new LambdaQueryWrapper<>();
    cond.accept(w);
    List<T> data = List.ofAll(mapper.selectList(w));
    if (data.size() > 1) {
      log.warn("got multiple result when try select one: {} - {}", mapper, cond);
    }
    return data.headOption();
  }

  /**
   * 使用 mapper 根据给定条件查询一组结果
   *
   * @param mapper MyBatis mapper
   * @param cond 其中可以定制查询条件，如可以传递 {@code w -> { w.eq(T::getId, 1); }}
   * @param <T> Map 的对象
   * @return 查询结果
   */
  public static <T> List<T> selectList(BaseMapper<T> mapper, Consumer<LambdaQueryWrapper<T>> cond) {
    LambdaQueryWrapper<T> w = new LambdaQueryWrapper<>();
    cond.accept(w);
    return List.ofAll(mapper.selectList(w));
  }

  /**
   * 同 {@link #selectList(BaseMapper, Consumer)} 功能一样，区别在于 {@code returnsEmpty} 参数
   *
   * @param returnsEmpty 如果为 true，将不做任何查询直接返回空列表
   */
  public static <T> List<T> selectList(
      BaseMapper<T> mapper, Consumer<LambdaQueryWrapper<T>> cond, boolean returnsEmpty) {
    if (returnsEmpty) {
      return API.List();
    } else {
      return selectList(mapper, cond);
    }
  }

  /**
   * 同 {@link #selectList(BaseMapper, Consumer)} 功能一样，区别在于 {@code returnsEmpty} 参数
   *
   * @param returnsEmpty 如果该 supplier 执行返回 true，将不做任何查询直接返回空列表
   */
  public static <T> List<T> selectList(
      BaseMapper<T> mapper, Consumer<LambdaQueryWrapper<T>> cond, Supplier<Boolean> returnsEmpty) {
    if (returnsEmpty.get()) {
      return API.List();
    } else {
      return selectList(mapper, cond);
    }
  }

  /**
   * @param mapper MyBatis mapper
   * @param cond 定制查询条件
   * @param pageNum 从 {@code 0} 开始计数的页数
   * @param pageSize 页大小
   * @param orders
   *     <li>{@code Tuple._1} 表示排序的列名
   *     <li>{@code Tuple._2} true 表示升序（asc）
   * @param <T> Map 的对象
   * @return
   *     <li>{@code Tuple._1} 当前页数据
   *     <li>{@code Tuple._2} 总页数
   *     <li>{@code Tuple._3} 数据总数
   */
  public static <T> Tuple3<List<T>, Long, Long> selectPage(
      BaseMapper<T> mapper,
      Consumer<LambdaQueryWrapper<T>> cond,
      int pageNum,
      int pageSize,
      List<Tuple2<String, Boolean>> orders) {
    Page<T> page = new Page<>(pageNum + 1, pageSize);
    if (orders.nonEmpty()) {
      page.setOrders(orders.map(v -> new OrderItem().setColumn(v._1).setAsc(v._2)).toJavaList());
    }
    LambdaQueryWrapper<T> w = new LambdaQueryWrapper<>();
    cond.accept(w);
    mapper.selectPage(page, w);
    List<T> data = List.ofAll(page.getRecords());
    long pages = page.getPages();
    long total = page.getTotal();
    return API.Tuple(data, pages, total);
  }

  /**
   * @param mapper MyBatis mapper
   * @param cond 查询条件
   * @param pageNum 从 {@code 0} 开始计数的页数
   * @param pageSize 页大小
   * @param orders
   *     <li>{@code Tuple._1} 表示排序的列名
   *     <li>{@code Tuple._2} true 表示升序（asc）
   * @param <T> Map 的对象
   * @return
   *     <li>{@code Tuple._1} 当前页数据
   *     <li>{@code Tuple._2} 总页数
   *     <li>{@code Tuple._3} 数据总量
   */
  public static <T> Tuple3<List<T>, Long, Long> selectPage(
      BaseMapper<T> mapper,
      LambdaQueryWrapper<T> cond,
      int pageNum,
      int pageSize,
      List<Tuple2<String, Boolean>> orders) {
    Page<T> page = new Page<>(pageNum + 1, pageSize);
    if (orders.nonEmpty()) {
      page.setOrders(orders.map(v -> new OrderItem().setColumn(v._1).setAsc(v._2)).toJavaList());
    }
    mapper.selectPage(page, cond);
    List<T> data = List.ofAll(page.getRecords());
    long pages = page.getPages();
    long total = page.getTotal();
    return API.Tuple(data, pages, total);
  }

  /**
   * @param mapper MyBatis mapper
   * @param update 更新条件及更新数据
   * @param <T> Map 的数据
   */
  public static <T> int update(BaseMapper<T> mapper, Consumer<LambdaUpdateWrapper<T>> update) {
    LambdaUpdateWrapper<T> w = new LambdaUpdateWrapper<>();
    update.accept(w);
    return mapper.update(null, w);
  }

  /**
   * @param mapper MyBatis mapper
   * @param data 非空字段将用于更新
   * @param updateCond 用于构造更新条件
   * @param <T> Map 的数据
   */
  public static <T> int update(
      BaseMapper<T> mapper, T data, Consumer<LambdaQueryWrapper<T>> updateCond) {
    LambdaQueryWrapper<T> w = new LambdaQueryWrapper<>();
    updateCond.accept(w);
    return mapper.update(data, w);
  }

  /**
   * 注册 {@link JacksonTypeHandler} 作为 MyBatis Json 相关字段的类型转换器
   *
   * @param typeHandlerRegistry MyBatis 类型转换器注册
   */
  public static void registerJacksonTypeHandlers(TypeHandlerRegistry typeHandlerRegistry) {
    typeHandlerRegistry.register(JsonNode.class, JacksonTypeHandler.class);
    typeHandlerRegistry.register(ObjectNode.class, JacksonTypeHandler.class);
    typeHandlerRegistry.register(ArrayNode.class, JacksonTypeHandler.class);
  }

  /**
   * 注册 {@link BinAndUUIDTypeHandler} 作为数据库中 {@code BINARY} 到 Java {@code UUID} 的类型转换器
   *
   * @param typeHandlerRegistry MyBatis 类型转换器注册
   */
  public static void registerBinAndUUIDTypeHandler(TypeHandlerRegistry typeHandlerRegistry) {
    typeHandlerRegistry.register(UUID.class, JdbcType.BINARY, BinAndUUIDTypeHandler.getInstance());
  }

  /**
   * 注册 {@link BinAndEntityIdTypeHandler} 作为数据库中 {@code BINARY} 到 Java {@code EntityId} 的类型转换器
   *
   * @param typeHandlerRegistry MyBatis 类型转换器注册
   * @param clazz EntityId 类型类
   * @param <T> 具体的 EntityId 类型
   */
  public static <T extends EntityId> void registerBinAndEntityIdTypeHandler(
      TypeHandlerRegistry typeHandlerRegistry, Class<T> clazz) {
    typeHandlerRegistry.register(clazz, JdbcType.BINARY, new BinAndEntityIdTypeHandler<>(clazz));
  }

  /**
   * 注册 {@link LongAndEntityIdTypeHandler} 作为数据库中 {@code BIGINT} 或者 {@code INTEGER} 到 Java {@code
   * EntityId} 的类型转换器
   *
   * @param typeHandlerRegistry MyBatis 类型转换器注册
   * @param clazz EntityId 类型类
   * @param <T> 具体的 EntityId 类型
   */
  public static <T extends EntityId> void registerLongToEntityIdTypeHandler(
      TypeHandlerRegistry typeHandlerRegistry, Class<T> clazz) {
    typeHandlerRegistry.register(clazz, JdbcType.BIGINT, new LongAndEntityIdTypeHandler<>(clazz));
    typeHandlerRegistry.register(clazz, JdbcType.INTEGER, new LongAndEntityIdTypeHandler<>(clazz));
  }
}
