package com.msjc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msjc.tools.data.HasId;
import java.io.Serializable;
import java.util.UUID;

public interface EntityId extends HasId<UUID>, Serializable {

  long DEFAULT_MOST_SIGNIFICANT_BYTES = 424242424242424242L;

  UUID NULL_UUID = new UUID(0, 0);

  UUID getId();

  String getEntityType();

  @JsonIgnore
  default boolean isNullId() {
    return NULL_UUID.equals(getId());
  }
}
