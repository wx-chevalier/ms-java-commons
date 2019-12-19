package udla.domain.event;

public interface EventHandler<E extends Event> {

  Class<E> eventClass();

  void handle(E event);
}
