package schwarz.it.ae.bookmarx.entries.api;

import schwarz.it.ae.bookmarx.core.domain.Bookmark;

public class JsonSingleBookmarkResponse {
    public JsonBookmark bookmark;


    public static JsonSingleBookmarkResponse create(Bookmark bookmark) {
        JsonSingleBookmarkResponse response = new JsonSingleBookmarkResponse();
        response.bookmark = new JsonBookmark.Builder()
                        .bookmark(bookmark)
                        .build();
        return response;
    }
}
