package schwarz.it.ae.bookmarx.dataproviders.inmem;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import schwarz.it.ae.bookmarx.MongoTestContainerExtension;
import schwarz.it.ae.bookmarx.TestConfigMongo;
import schwarz.it.ae.bookmarx.core.usecases.BookmarkProvider;
import schwarz.it.ae.bookmarx.dataproviders.AbstractBookmarkProviderTestCollection;

class InMemoryBookmarkProviderTest extends AbstractBookmarkProviderTestCollection {


    private BookmarkProvider bookmarkProvider = new InMemoryBookmarkProvider();

    @Override
    protected BookmarkProvider getBookmarkProvider() {
        return bookmarkProvider;
    }

}
