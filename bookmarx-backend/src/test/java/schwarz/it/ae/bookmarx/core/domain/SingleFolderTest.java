package schwarz.it.ae.bookmarx.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleFolderTest {

    @Test
    public void testCreate() {
        SingleFolder f = new SingleFolder("F1");

        assertEquals(true, f.getId().exists());
        assertEquals("F1", f.getName());
        assertEquals(false, f.getParentId().exists());
    }

}
