package schwarz.it.ae.bookmarx;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(PostgresSharedTestContainerExtension.class)
class BookmarkxBackendApplicationIT {

  @Test
  void contextLoads() {
  }

}
