package schwarz.it.ae.bookmarx.core.domain;

import java.net.MalformedURLException;
import java.net.URL;

public class BookmarkUrl {
  private URL url;

  public BookmarkUrl(String url) {
    try {
      this.url = new URL(url);
    } catch (MalformedURLException e) {
      this.url = null;
    }
  }

  public URL asUrl() {
    return url;
  }

  public String asString() {
    if (url == null) {
      return "";
    }
    return url.toString();
  }

  public URL getFacIconUrl() {
    try {
      return new URL(url.getProtocol(), url.getHost(), url.getPort(), "/favicon.ico");
    } catch (MalformedURLException e) {
      return null;
    }
  }
}
