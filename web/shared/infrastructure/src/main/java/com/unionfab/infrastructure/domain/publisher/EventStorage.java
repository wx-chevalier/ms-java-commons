package com.msjc.infrastructure.domain.publisher;

import com.msjc.domain.DomainEvent;
import io.vavr.collection.List;

public interface EventStorage {

  void save(DomainEvent event);

  List<DomainEvent> toPublish();

  void published(List<DomainEvent> events);
}
