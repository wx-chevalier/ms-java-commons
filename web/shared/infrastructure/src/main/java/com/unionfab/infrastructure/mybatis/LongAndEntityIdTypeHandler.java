package com.msjc.infrastructure.mybatis;

import com.msjc.domain.EntityId;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * {@link com.msjc.domain.EntityId} 和表 bigint/int 类型主键的映射
 *
 * @param <T> 实体 ID 类型
 */
public class LongAndEntityIdTypeHandler<T extends EntityId> extends BaseTypeHandler<T> {

  private final Function<Long, T> construct;

  public LongAndEntityIdTypeHandler(Class<T> typeClazz) {
    this.construct = $MyBatis.createLongConstructorForEntityId(typeClazz);
  }

  @Override
  public void setNonNullParameter(
      PreparedStatement preparedStatement, int i, T t, JdbcType jdbcType) throws SQLException {
    preparedStatement.setLong(i, t.getId().getLeastSignificantBits());
  }

  @Override
  public T getNullableResult(ResultSet resultSet, String s) throws SQLException {
    return construct.apply(resultSet.getLong(s));
  }

  @Override
  public T getNullableResult(ResultSet resultSet, int i) throws SQLException {
    return construct.apply(resultSet.getLong(i));
  }

  @Override
  public T getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
    return construct.apply(callableStatement.getLong(i));
  }
}
