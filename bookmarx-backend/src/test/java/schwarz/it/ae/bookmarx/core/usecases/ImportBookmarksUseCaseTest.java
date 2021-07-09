package schwarz.it.ae.bookmarx.core.usecases;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import schwarz.it.ae.bookmarx.dataproviders.inmem.InMemoryBookmarkProvider;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImportBookmarksUseCaseTest {

    @Test
    public void testImportFromFirefoxExport() throws Exception {

        String exportFilename = "/ff-export-example-simple.json";


        String firefoxExportAsJsonString = IOUtils.toString(
                this.getClass().getResourceAsStream(exportFilename), StandardCharsets.UTF_8
        );

        InMemoryBookmarkProvider inMemoryBookmarkProvider = new InMemoryBookmarkProvider();
        ImportBookmarksUseCase importBookmarksUseCase = new ImportBookmarksUseCase(inMemoryBookmarkProvider);

        importBookmarksUseCase.importFirefoxExport(firefoxExportAsJsonString);

        assertEquals(3, inMemoryBookmarkProvider.findAllFolders().size());


    }

}
