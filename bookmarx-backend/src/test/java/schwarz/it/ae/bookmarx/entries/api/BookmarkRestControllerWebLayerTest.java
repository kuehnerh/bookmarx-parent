package schwarz.it.ae.bookmarx.entries.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import schwarz.it.ae.bookmarx.core.usecases.CrudBookmarkUseCase;
import schwarz.it.ae.bookmarx.core.usecases.CrudFolderUseCase;

import java.util.ArrayList;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests only the WebLayer (without starting a HTTP-Server)
 * Everything below the WebLayer (e. g. UseCases) are Mocked
 *
 * Further Info to Web-Layer Testing see: https://spring.io/guides/gs/testing-web/
 */
@WebMvcTest
class BookmarkRestControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrudBookmarkUseCase crudBookmarkUseCase;

    @MockBean
    private CrudFolderUseCase crudFolderUseCase;

    @Test
    public void getAllBookmarks() throws Exception {
        when(crudBookmarkUseCase.getAllBookmarks()).thenReturn(new ArrayList<>());

        this.mockMvc
                .perform(get("/api/bookmarks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("bookmarks")));

    }
}
