package schwarz.it.ae.bookmarx.dataproviders.mongodb;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import schwarz.it.ae.bookmarx.dataproviders.postgres.BookmarkJpaEntity;


import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookmarkMongoEntity {

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
        private final BookmarkMongoEntity newBookmarkMongoEntity = new BookmarkMongoEntity();

        public Builder id(String id) {
            newBookmarkMongoEntity.id = id;
            return this;
        }

        public Builder title(String title) {
            newBookmarkMongoEntity.title = title;
            return this;
        }

        public Builder url(String url) {
            newBookmarkMongoEntity.url = url;
            return this;
        }

        public Builder description(String description) {
            newBookmarkMongoEntity.description = description;
            return this;
        }

        public Builder tags(List<String> tagList) {
            newBookmarkMongoEntity.tags = StringUtils.join(tagList, ',');
            return this;
        }


        public Builder createdAt(Instant createdAt) {
            newBookmarkMongoEntity.createdAt = createdAt;
            return this;
        }

        public Builder modifiedAt(Instant modifiedAt) {
            newBookmarkMongoEntity.modifiedAt = modifiedAt;
            return this;
        }

        public BookmarkMongoEntity build() {
            return newBookmarkMongoEntity;
        }
    }
}
