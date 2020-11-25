package com.msjc.infrastructure.mybatis;

import static com.msjc.infrastructure.mybatis.$MyBatis.bytesToUUID;
import static com.msjc.infrastructure.mybatis.$MyBatis.getUUIDIdConstructor;
import static com.msjc.infrastructure.mybatis.$MyBatis.uuidToBytes;

import com.msjc.domain.EntityId;
import io.vavr.API;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.function.Function;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class BinAndEntityIdTypeHandler<T extends EntityId> extends BaseTypeHandler<T> {

  private final Function<UUID, T> construct;

  public BinAndEntityIdTypeHandler(Class<T> typeClazz) {
    this.construct = getUUIDIdConstructor(typeClazz);
  }

  @Override
  public void setNonNullParameter(
      PreparedStatement preparedStatement, int i, T t, JdbcType jdbcType) throws SQLException {
    preparedStatement.setBytes(i, uuidToBytes(t.getId()));
  }

  @Override
  public T getNullableResult(ResultSet resultSet, String s) throws SQLException {
    return API.Option(bytesToUUID(resultSet.getBytes(s))).map(construct).getOrNull();
  }

  @Override
  public T getNullableResult(ResultSet resultSet, int i) throws SQLException {
    return API.Option(bytesToUUID(resultSet.getBytes(i))).map(construct).getOrNull();
  }

  @Override
  public T getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
    return API.Option(bytesToUUID(callableStatement.getBytes(i))).map(construct).getOrNull();
  }

  @Override
  public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setBytes(i, uuidToBytes(parameter.getId()));
  }

  @Override
  public T getResult(ResultSet rs, String columnName) throws SQLException {
    return API.Option(bytesToUUID(rs.getBytes(columnName))).map(construct).getOrNull();
  }

  @Override
  public T getResult(ResultSet rs, int columnIndex) throws SQLException {
    return API.Option(bytesToUUID(rs.getBytes(columnIndex))).map(construct).getOrNull();
  }

  @Override
  public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
    return API.Option(bytesToUUID(cs.getBytes(columnIndex))).map(construct).getOrNull();
  }
}
