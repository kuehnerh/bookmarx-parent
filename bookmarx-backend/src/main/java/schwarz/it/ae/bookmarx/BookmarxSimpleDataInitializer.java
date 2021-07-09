package schwarz.it.ae.bookmarx;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import schwarz.it.ae.bookmarx.core.domain.Bookmark;
import schwarz.it.ae.bookmarx.core.domain.SingleFolder;
import schwarz.it.ae.bookmarx.core.usecases.BookmarkProvider;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class BookmarxSimpleDataInitializer implements ApplicationRunner {

    private final BookmarkProvider bookmarkProvider;

    public BookmarxSimpleDataInitializer(BookmarkProvider bookmarkProvider) {
        this.bookmarkProvider = bookmarkProvider;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Clear all
        bookmarkProvider.deleteAllAssignments();
        bookmarkProvider.deleteAllBookmarks();
        bookmarkProvider.deleteAllFolders();

        // Sample Bookmarks
        Bookmark bmExample = bookmarkProvider.saveBookmark(
                new Bookmark.Builder()
                        .title("Example-Title")
                        .url("https://www.example.com")
                        .description("Example Description")
                        .createdAt(Instant.now().minus(10, ChronoUnit.MINUTES))
                        .tags("t1", "t2")
                        .modifiedAt(Instant.now())
                        .build());

        Bookmark bmTagesschau = bookmarkProvider.saveBookmark(
                new Bookmark.Builder()
                        .title("Tagesschau")
                        .url("https://www.tagesschau.de")
                        .description("News of the World")
                        .createdAt(Instant.now().minus(10, ChronoUnit.MINUTES))
                        .modifiedAt(Instant.now())
                        .build());

        Bookmark bmHeise = bookmarkProvider.saveBookmark(
                new Bookmark.Builder()
                        .title("Heise")
                        .url("https://www.heise.de")
                        .description("IT News")
                        .createdAt(Instant.now().minus(10, ChronoUnit.MINUTES))
                        .modifiedAt(Instant.now())
                        .build());

        // Sample Folders
        SingleFolder rootFolder1 = new SingleFolder("IT-Stuff");
        bookmarkProvider.saveFolder(rootFolder1);
        SingleFolder fArchitecture = bookmarkProvider.saveFolder(new SingleFolder("Architecture", rootFolder1.getId()));
        bookmarkProvider.saveFolder(new SingleFolder("Clean Architecture", fArchitecture.getId()));
        bookmarkProvider.saveFolder(new SingleFolder("Bad Architecture", fArchitecture.getId()));
        bookmarkProvider.saveFolder(new SingleFolder("Agile", rootFolder1.getId()));

        bookmarkProvider.saveFolder(
                new SingleFolder("IT-News", rootFolder1.getId())
                        .assignBookmarkId(bmHeise.getId()));

        bookmarkProvider.saveFolder(
                new SingleFolder("Other")
                        .assignBookmarkId(bmHeise.getId())
                        .assignBookmarkId(bmTagesschau.getId())
                        .assignBookmarkId(bmExample.getId()));

    }
}
