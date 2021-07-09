package schwarz.it.ae.bookmarx;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import schwarz.it.ae.bookmarx.core.usecases.BookmarkProvider;
import schwarz.it.ae.bookmarx.dataproviders.postgres.FolderBookmarkAssignmentJpaRepository;
import schwarz.it.ae.bookmarx.dataproviders.postgres.FolderJpaRepository;
import schwarz.it.ae.bookmarx.dataproviders.postgres.BookmarkJpaRepository;
import schwarz.it.ae.bookmarx.dataproviders.postgres.PostgresBookmarkProvider;

@TestConfiguration
public class TestConfigPostgres {


    @Bean
    public BookmarkProvider getJpaRepositoryBookmarkProvider(
            BookmarkJpaRepository bookmarkJpaRepository,
            FolderJpaRepository folderJpaRepository,
            FolderBookmarkAssignmentJpaRepository folderBookmarkAssignmentJpaRepository) {
        return new PostgresBookmarkProvider(bookmarkJpaRepository, folderJpaRepository, folderBookmarkAssignmentJpaRepository);
    }

}
