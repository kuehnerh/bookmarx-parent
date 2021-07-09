package schwarz.it.ae.bookmarx.entries.api;

import schwarz.it.ae.bookmarx.core.domain.Bookmark;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class JsonBookmark {
  private String id;
  private String title;
  private String url;
  private String description;
  private List<String> tags;
  private Instant createdAt;
  private Instant modifiedAt;

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getUrl() {
    return url;
  }

  public String getDescription() {
    return description;
  }

  public List<String> getTags() {
    return tags;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getModifiedAt() {
    return modifiedAt;
  }


  public Bookmark toBookmark(String bookmarkId) {
    return new Bookmark.Builder()
            .id(bookmarkId)
            .title(title)
            .url(url)
            .description(description)
            .tags(tags)
            .modifiedAt(modifiedAt)
            .createdAt(createdAt)
            .build();
  }

  public Bookmark toBookmark() {
    return toBookmark(id);
  }


  public static class Builder {
    JsonBookmark newJsonBookmark = new JsonBookmark();

    public Builder bookmark(Bookmark bookmark) {
      newJsonBookmark.id = bookmark.getId().asString();
      newJsonBookmark.title = bookmark.getTitle().asString();
      newJsonBookmark.url = bookmark.getUrl().asString();
      newJsonBookmark.description = bookmark.getDescription();
      newJsonBookmark.tags = bookmark.getTags().stream()
        .map(tag -> tag.asString())
        .collect(Collectors.toList());
      newJsonBookmark.createdAt = bookmark.getCreatedAt();
      newJsonBookmark.modifiedAt = bookmark.getModifiedAt();

      return this;
    }

    public JsonBookmark build() {
      return newJsonBookmark;
    }

  }

}
