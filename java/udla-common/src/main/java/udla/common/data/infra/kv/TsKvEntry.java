package udla.common.data.infra.kv;

public interface TsKvEntry<T> extends KvEntry<T> {
  long getTs();
}
