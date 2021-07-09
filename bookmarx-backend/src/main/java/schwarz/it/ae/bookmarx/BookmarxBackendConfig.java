package schwarz.it.ae.bookmarx;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import schwarz.it.ae.bookmarx.core.usecases.BookmarkProvider;
import schwarz.it.ae.bookmarx.core.usecases.CrudBookmarkUseCase;
import schwarz.it.ae.bookmarx.core.usecases.CrudFolderUseCase;
import schwarz.it.ae.bookmarx.core.usecases.ImportBookmarksUseCase;
import schwarz.it.ae.bookmarx.dataproviders.inmem.InMemoryBookmarkProvider;
import schwarz.it.ae.bookmarx.dataproviders.mongodb.BookmarkFolderMongoRepository;
import schwarz.it.ae.bookmarx.dataproviders.mongodb.BookmarkMongoRepository;
import schwarz.it.ae.bookmarx.dataproviders.mongodb.MongoBookmarkProvider;
import schwarz.it.ae.bookmarx.dataproviders.postgres.FolderBookmarkAssignmentJpaRepository;
import schwarz.it.ae.bookmarx.dataproviders.postgres.FolderJpaRepository;
import schwarz.it.ae.bookmarx.dataproviders.postgres.BookmarkJpaRepository;
import schwarz.it.ae.bookmarx.dataproviders.postgres.PostgresBookmarkProvider;

@Configuration
public class BookmarxBackendConfig {

  @Bean
  public CrudBookmarkUseCase getCrudUseCase(BookmarkProvider bookmarkProvider) {
    return new CrudBookmarkUseCase(bookmarkProvider);
  }

  @Bean
  public CrudFolderUseCase getFolderCrudUseCase(BookmarkProvider bookmarkProvider) {
    return new CrudFolderUseCase(bookmarkProvider);
  }

  @Bean
  public ImportBookmarksUseCase getImportBookmarksUseCase(BookmarkProvider bookmarkProvider) {
    return new ImportBookmarksUseCase(bookmarkProvider);
  }

  @Bean("jpaRepositoryBookmarkProvider")
  @Primary
  public BookmarkProvider getJpaRepositoryBookmarkProvider(
          BookmarkJpaRepository bookmarkJpaRepository,
          FolderJpaRepository folderJpaRepository,
          FolderBookmarkAssignmentJpaRepository folderBookmarkAssignmentJpaRepository) {

    return new PostgresBookmarkProvider(
            bookmarkJpaRepository, folderJpaRepository, folderBookmarkAssignmentJpaRepository);
  }

  @Bean("mongoRepositoryBookmarkProvider")
  public BookmarkProvider getMongoRepositoryBookmarkProvider(
          BookmarkMongoRepository bookmarkMongoRepository,
          BookmarkFolderMongoRepository bookmarkFolderMongoRepository) {

    return new MongoBookmarkProvider(
            bookmarkMongoRepository, bookmarkFolderMongoRepository);
  }


  @Bean("inMemoryBookmarkProvider")
  public BookmarkProvider getInMemoryBookmarkProvider() {
    return new InMemoryBookmarkProvider();
  }


  @Bean
  public BookmarxSimpleDataInitializer getSimpleDataInitializer(BookmarkProvider bookmarkProvider) {
    return new BookmarxSimpleDataInitializer(bookmarkProvider);
  }

//  @Bean
//  public BookmarxFirefoxImportDataInitializer getFirefoxImportDataInitializer(BookmarkProvider bookmarkProvider, ImportBookmarksUseCase importBookmarksUseCase) {
//    return new BookmarxFirefoxImportDataInitializer(bookmarkProvider, importBookmarksUseCase);
//  }

}
