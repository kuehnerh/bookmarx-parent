package schwarz.it.ae.bookmarx;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import schwarz.it.ae.bookmarx.core.usecases.BookmarkProvider;
import schwarz.it.ae.bookmarx.dataproviders.mongodb.BookmarkFolderMongoRepository;
import schwarz.it.ae.bookmarx.dataproviders.mongodb.BookmarkMongoRepository;
import schwarz.it.ae.bookmarx.dataproviders.mongodb.MongoBookmarkProvider;

@TestConfiguration
public class TestConfigMongo {

    @Bean("mongoRepositoryBookmarkProvider")
    public BookmarkProvider getMongoRepositoryBookmarkProvider(
            BookmarkMongoRepository bookmarkMongoRepository,
            BookmarkFolderMongoRepository bookmarkFolderMongoRepository) {
        return new MongoBookmarkProvider(bookmarkMongoRepository, bookmarkFolderMongoRepository);
    }
}
