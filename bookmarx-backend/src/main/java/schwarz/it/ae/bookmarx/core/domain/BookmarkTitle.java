package schwarz.it.ae.bookmarx.core.domain;

public class BookmarkTitle {
  private String title;

  public BookmarkTitle(String title) {
    this.title = title;
  }

  public String asString() {
    return title;
  }

  @Override
  public String toString() {
    return "BookmarkTitle{" +
      "title='" + title + '\'' +
      '}';
  }
}
