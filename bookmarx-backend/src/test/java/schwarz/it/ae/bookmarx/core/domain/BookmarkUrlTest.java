package schwarz.it.ae.bookmarx.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookmarkUrlTest {

    @Test
    public void testFavIconUrl() {
        BookmarkUrl bookmarkUrl = new BookmarkUrl("https://www.example.com");
        assertEquals("https://www.example.com/favicon.ico", bookmarkUrl.getFacIconUrl().toString());

        bookmarkUrl = new BookmarkUrl("https://www.example.com:80");
        assertEquals("https://www.example.com:80/favicon.ico", bookmarkUrl.getFacIconUrl().toString());

        bookmarkUrl = new BookmarkUrl("https://www.example.com/");
        assertEquals("https://www.example.com/favicon.ico", bookmarkUrl.getFacIconUrl().toString());

        bookmarkUrl = new BookmarkUrl("https://www.example.com/test.html");
        assertEquals("https://www.example.com/favicon.ico", bookmarkUrl.getFacIconUrl().toString());

        bookmarkUrl = new BookmarkUrl("https://www.example.com/test.html?q1=123");
        assertEquals("https://www.example.com/favicon.ico", bookmarkUrl.getFacIconUrl().toString());
    }

}
