package udla.common.data.infra.kv.query;

import udla.common.data.infra.kv.Aggregation;

public interface ReadTsKvQuery extends TsKvQuery {
  Long getInterval();

  Integer getLimit();

  Aggregation getAggregation();

  String getOrderBy();
}
