package schwarz.it.ae.bookmarx.entries.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import schwarz.it.ae.bookmarx.PostgresSharedTestContainerExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the whole Application
 *
 * Further Info to Web-Layer Testing see: https://spring.io/guides/gs/testing-web/
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(PostgresSharedTestContainerExtension.class)
class BookmarkRestControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllBookmarks() throws Exception {

        String result = this.restTemplate
                .getForObject("http://localhost:" + port + "/api/bookmarks", String.class);

        System.out.println(result);

        assertThat(result.contains("bookmarks"));
    }
}
