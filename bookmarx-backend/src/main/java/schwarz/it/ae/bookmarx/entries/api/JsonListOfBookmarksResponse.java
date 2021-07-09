package schwarz.it.ae.bookmarx.entries.api;

import schwarz.it.ae.bookmarx.core.domain.Bookmark;

import java.util.List;
import java.util.stream.Collectors;

public class JsonListOfBookmarksResponse {
  public List<JsonBookmark> bookmarks;


  public static JsonListOfBookmarksResponse create(List<Bookmark> bookmarkList) {
    JsonListOfBookmarksResponse response = new JsonListOfBookmarksResponse();
    response.bookmarks = bookmarkList.stream()
      .map(bookmark -> new JsonBookmark.Builder()
        .bookmark(bookmark)
        .build())
      .collect(Collectors.toList());
    return response;
  }
}
