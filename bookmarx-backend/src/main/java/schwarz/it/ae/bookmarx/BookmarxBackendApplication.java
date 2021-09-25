package schwarz.it.ae.bookmarx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookmarxBackendApplication {

  /**
   * To get rid of Liquibase Warning "Skipping auto-registration" in SpringBoot Startup Log
   * you have to add this VM parameter: -Dliquibase.hub.mode=off
   *
   * see: https://github.com/liquibase/liquibase/issues/1741
   *
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(BookmarxBackendApplication.class, args);
  }

}
