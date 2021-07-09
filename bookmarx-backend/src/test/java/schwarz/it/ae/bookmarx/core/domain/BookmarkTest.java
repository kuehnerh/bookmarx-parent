package schwarz.it.ae.bookmarx.core.domain;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookmarkTest {

  @Test
  public void testBookmarkBuilder() {
    Bookmark b = new Bookmark.Builder()
            .id(null)
            .title("T1")
            .url("https://www.heise.de")
            .description("D1")
            .tags(new String[] {"tag1", "tag2"})
            .build();

    assertEquals(StringUtils.EMPTY, b.getId().asString());
    assertEquals("T1", b.getTitle().asString());
    assertEquals("https://www.heise.de", b.getUrl().asString());
    assertEquals("D1", b.getDescription());
    assertArrayEquals(
            new String[] {"tag1", "tag2"},
            b.getTags().stream().map(tag -> tag.asString()).toArray(String[]::new));
  }

}
