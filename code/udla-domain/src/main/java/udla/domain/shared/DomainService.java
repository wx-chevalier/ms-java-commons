package udla.domain.shared;

import udla.domain.event.EventBus;

public interface DomainService {

  EventBus getEventBus();
}
