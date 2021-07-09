package schwarz.it.ae.bookmarx.core.domain;

import org.apache.commons.lang3.StringUtils;

public class BookmarkTag {
  private String tag;

  public BookmarkTag(String tag) {
    if (StringUtils.isEmpty(tag)) {
      this.tag = StringUtils.EMPTY;
    } else {
      this.tag = tag;
    }
  }

  public String asString() {
    return tag;
  }
}
