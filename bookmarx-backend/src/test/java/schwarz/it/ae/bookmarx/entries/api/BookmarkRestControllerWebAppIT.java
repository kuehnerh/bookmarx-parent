package schwarz.it.ae.bookmarx.entries.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import schwarz.it.ae.bookmarx.PostgresSharedTestContainerExtension;
import schwarz.it.ae.bookmarx.PostgresTestContainerExtension;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests the whole Application but without starting a HTTP-Server.
 * Needs a running Database.
 *
 * Further Info to Web-Layer Testing see: https://spring.io/guides/gs/testing-web/
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(PostgresSharedTestContainerExtension.class)
class BookmarkRestControllerWebAppIT {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getAllBookmarks() throws Exception {
        this.mockMvc
                .perform(get("/api/bookmarks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("bookmarks")));
    }
}
