package schwarz.it.ae.bookmarx.core.domain;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntityIdTest {

    @Test
    public void testIdWithNull() {
        EntityId id = new EntityId(null);
        assertEquals(StringUtils.EMPTY, id.asString());
        assertEquals(false, id.exists());
    }

    @Test
    public void testIdWithEmptyString() {
        EntityId id = new EntityId(StringUtils.EMPTY);
        assertEquals(StringUtils.EMPTY, id.asString());
        assertEquals(false, id.exists());
    }

    @Test
    public void testIdWithUuidString() {
        String uuidString = UUID.randomUUID().toString();
        EntityId id = new EntityId(uuidString);
        assertEquals(uuidString, id.asString());
        assertEquals(true, id.exists());
    }

    @Test
    void testEqualsAndHashcode() {
        String uuidString = UUID.randomUUID().toString();
        EntityId id1 = new EntityId(uuidString);
        EntityId id2 = new EntityId(uuidString);
        assertTrue(id1.equals(id2));
        assertEquals(id1.hashCode(), id2.hashCode());
    }


}
