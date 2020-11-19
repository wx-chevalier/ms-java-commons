package com.unionfab.infrastructure.domain.publisher;

import com.unionfab.domain.DomainEvent;
import com.unionfab.domain.DomainEvents;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@AllArgsConstructor
public class JustForwardDomainEventPublisher implements DomainEvents {

  private final ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void publish(DomainEvent event) {
    applicationEventPublisher.publishEvent(event);
  }
}
