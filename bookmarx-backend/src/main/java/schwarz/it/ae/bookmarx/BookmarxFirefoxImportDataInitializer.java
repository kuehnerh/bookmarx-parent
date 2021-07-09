package schwarz.it.ae.bookmarx;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import schwarz.it.ae.bookmarx.core.usecases.BookmarkProvider;
import schwarz.it.ae.bookmarx.core.usecases.ImportBookmarksUseCase;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class BookmarxFirefoxImportDataInitializer implements ApplicationRunner {

    private final BookmarkProvider bookmarkProvider;

    private final ImportBookmarksUseCase importBookmarksUseCase;

    public BookmarxFirefoxImportDataInitializer(BookmarkProvider bookmarkProvider, ImportBookmarksUseCase importBookmarksUseCase) {

        this.bookmarkProvider = bookmarkProvider;
        this.importBookmarksUseCase = importBookmarksUseCase;

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Import Firefox Export
        String exportFilename = "/bookmarks-2021-08-14.json";
        InputStream is = this.getClass().getResourceAsStream(exportFilename);
        if (is != null) {
            String firefoxExportAsJsonString = IOUtils.toString(is, StandardCharsets.UTF_8);
            this.bookmarkProvider.deleteAllFolders();
            importBookmarksUseCase.importFirefoxExport(firefoxExportAsJsonString);
        }
    }
}
