package schwarz.it.ae.bookmarx.core.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.UUID;

public class EntityId {
  private UUID uuid;


  public EntityId() {
    this.uuid = UUID.randomUUID();
  }

  public EntityId(String uuidAsString) {
    if (!StringUtils.isEmpty((uuidAsString))) {
      uuid = UUID.fromString(uuidAsString);
    } else {
      uuid = null;
    }
  }

  public String asString() {
    if (uuid == null) {
      return StringUtils.EMPTY;
    }
    return uuid.toString();
  }

  public boolean exists() {
    return uuid == null ? false : true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EntityId entityId = (EntityId) o;
    return Objects.equals(uuid, entityId.uuid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid);
  }
}
