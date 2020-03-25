package udla.common.data.infra.kv.query;

public interface DeleteTsKvQuery extends TsKvQuery {
  Boolean getRewriteLatestIfDeleted();
}
