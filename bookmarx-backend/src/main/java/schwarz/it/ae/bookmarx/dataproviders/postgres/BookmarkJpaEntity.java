package schwarz.it.ae.bookmarx.dataproviders.postgres;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name="BMX_BOOKMARK")
public class BookmarkJpaEntity {

    @Id
    private String id;

    private String title;
    private String url;
    private String description;
    private String tags;
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
        if (StringUtils.isNotEmpty(tags)) {
            return Arrays.asList(StringUtils.split(tags, ','));
        }
        return new ArrayList<>(0);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public static class Builder {
        private final BookmarkJpaEntity newBookmarkJpaEntity = new BookmarkJpaEntity();

        public Builder id(String id) {
            newBookmarkJpaEntity.id = id;
            return this;
        }

        public Builder title(String title) {
            newBookmarkJpaEntity.title = title;
            return this;
        }

        public Builder url(String url) {
            newBookmarkJpaEntity.url = url;
            return this;
        }

        public Builder description(String description) {
            newBookmarkJpaEntity.description = description;
            return this;
        }

        public Builder tags(List<String> tagList) {
            newBookmarkJpaEntity.tags = StringUtils.join(tagList, ',');
            return this;
        }

        public Builder createdAt(Instant createdAt) {
            newBookmarkJpaEntity.createdAt = createdAt;
            return this;
        }

        public Builder modifiedAt(Instant modifiedAt) {
            newBookmarkJpaEntity.modifiedAt = modifiedAt;
            return this;
        }

        public BookmarkJpaEntity build() {
            return newBookmarkJpaEntity;
        }
    }
}
