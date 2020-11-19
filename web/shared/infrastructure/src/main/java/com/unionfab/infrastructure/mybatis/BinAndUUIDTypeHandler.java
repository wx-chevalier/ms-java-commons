package com.unionfab.infrastructure.mybatis;

import static com.unionfab.infrastructure.mybatis.$MyBatis.bytesToUUID;
import static com.unionfab.infrastructure.mybatis.$MyBatis.uuidToBytes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import lombok.Getter;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class BinAndUUIDTypeHandler extends BaseTypeHandler<UUID> {

  @Getter private static final BinAndUUIDTypeHandler instance = new BinAndUUIDTypeHandler();

  public BinAndUUIDTypeHandler() {}

  @Override
  public void setNonNullParameter(
      PreparedStatement preparedStatement, int i, UUID uuid, JdbcType jdbcType)
      throws SQLException {
    preparedStatement.setBytes(i, uuidToBytes(uuid));
  }

  @Override
  public UUID getNullableResult(ResultSet resultSet, String s) throws SQLException {
    return bytesToUUID(resultSet.getBytes(s));
  }

  @Override
  public UUID getNullableResult(ResultSet resultSet, int i) throws SQLException {
    return bytesToUUID(resultSet.getBytes(i));
  }

  @Override
  public UUID getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
    return bytesToUUID(callableStatement.getBytes(i));
  }

  @Override
  public void setParameter(PreparedStatement ps, int i, UUID parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setBytes(i, uuidToBytes(parameter));
  }

  @Override
  public UUID getResult(ResultSet rs, String columnName) throws SQLException {
    return bytesToUUID(rs.getBytes(columnName));
  }

  @Override
  public UUID getResult(ResultSet rs, int columnIndex) throws SQLException {
    return bytesToUUID(rs.getBytes(columnIndex));
  }

  @Override
  public UUID getResult(CallableStatement cs, int columnIndex) throws SQLException {
    return bytesToUUID(cs.getBytes(columnIndex));
  }
}
