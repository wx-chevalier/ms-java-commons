package com.unionfab.domain;

import com.google.common.util.concurrent.UncheckedExecutionException;

public interface DomainService {

  // 会先发送到同步 bus，确保同步事件处理全都执行完再将事件异步发送
  default void send(DomainEvent event) throws UncheckedExecutionException {}
}
