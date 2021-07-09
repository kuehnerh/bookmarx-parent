package schwarz.it.ae.bookmarx.core.domain;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Bookmark {
  private EntityId id;
  private BookmarkTitle title;
  private BookmarkUrl url;
  private String description;
  private String favPathIcon;
  private List<BookmarkTag> tags = new ArrayList<>();
  private Instant createdAt;
  private Instant modifiedAt;

  private Bookmark() {
    // Private Constructor to force usage of Builder
    // Intentionally empty
  }

  public EntityId getId() {
    return id;
  }

  public BookmarkTitle getTitle() {
    return title;
  }

  public BookmarkUrl getUrl() {
    return url;
  }

  public String getDescription() {
    return description;
  }

  public String getFavPathIcon() {
    return favPathIcon;
  }

  public List<BookmarkTag> getTags() {
    return tags;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getModifiedAt() {
    return modifiedAt;
  }

  public static class Builder {

    private final Bookmark newBookmark = new Bookmark();

    public Builder of(Bookmark bookmarkTemplate) {
      newBookmark.id = bookmarkTemplate.id;
      newBookmark.url = bookmarkTemplate.url;
      newBookmark.title = bookmarkTemplate.title;
      newBookmark.description = bookmarkTemplate.description;
      newBookmark.tags = bookmarkTemplate.tags;
      newBookmark.createdAt = bookmarkTemplate.createdAt;
      newBookmark.modifiedAt = bookmarkTemplate.modifiedAt;
      return this;
    }

    public Builder id(String id) {
      newBookmark.id = new EntityId(id);
      return this;
    }

    public Builder generateId() {
      newBookmark.id = new EntityId();
      return this;
    }
    public Builder title(String title) {
      newBookmark.title = new BookmarkTitle(title);
      return this;
    }

    public Builder url(String url) {
      newBookmark.url = new BookmarkUrl(url);
      return this;
    }

    public Builder description(String description) {
      newBookmark.description = description;
      return this;
    }

    public Builder tags(String... tagValues) {
      if (tagValues != null) {
        for (String tagValue : tagValues) {
          newBookmark.tags.add(new BookmarkTag(tagValue));
        }
      }
      return this;
    }

    public Builder tags(List<String> tagValues) {
      if (tagValues != null) {
        tags(tagValues.toArray(new String[0]));
      }
      return this;
    }

    public Builder tagsSeparatedBy(String tagValues, String separator) {
      tags(StringUtils.split(tagValues, separator));
      return this;
    }


    public Builder createdAt(Instant createdAt) {
      newBookmark.createdAt = createdAt;
      return this;
    }

    public Builder modifiedAt(Instant modifiedAt) {
      newBookmark.modifiedAt = modifiedAt;
      return this;
    }


    public Bookmark build() {
      if (newBookmark.id == null) {
        newBookmark.id = new EntityId();
      }
      return newBookmark;
    }

  }

}

