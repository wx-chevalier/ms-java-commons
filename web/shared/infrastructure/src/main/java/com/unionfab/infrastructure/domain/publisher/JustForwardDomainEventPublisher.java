package com.msjc.infrastructure.domain.publisher;

import com.msjc.domain.DomainEvent;
import com.msjc.domain.DomainEvents;
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
