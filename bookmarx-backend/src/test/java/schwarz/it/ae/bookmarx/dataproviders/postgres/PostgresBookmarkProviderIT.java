package schwarz.it.ae.bookmarx.dataproviders.postgres;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import schwarz.it.ae.bookmarx.PostgresSharedTestContainerExtension;
import schwarz.it.ae.bookmarx.TestConfigPostgres;
import schwarz.it.ae.bookmarx.core.usecases.BookmarkProvider;
import schwarz.it.ae.bookmarx.dataproviders.AbstractBookmarkProviderTestCollection;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfigPostgres.class)
@ExtendWith(PostgresSharedTestContainerExtension.class)
class PostgresBookmarkProviderIT extends AbstractBookmarkProviderTestCollection {

    @Autowired
    private BookmarkProvider bookmarkProvider;

    @Override
    protected BookmarkProvider getBookmarkProvider() {
        return bookmarkProvider;
    }
}
