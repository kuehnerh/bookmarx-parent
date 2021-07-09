package schwarz.it.ae.bookmarx;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.extension.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public class MongoTestContainerExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback {

    private final String MONGO_DB_NAME = "abc";

    private MongoDBContainer mongoContainer;
    private MongoClient mongoClient;


    @Override
    public void beforeAll(ExtensionContext context) {
        mongoContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.4.7-focal"));
        mongoContainer.start();

        System.setProperty("spring.data.mongodb.uri", mongoContainer.getReplicaSetUrl());
        System.setProperty("spring.data.mongodb.database", MONGO_DB_NAME);

        mongoClient = MongoClients.create(mongoContainer.getReplicaSetUrl());
    }


    @Override
    public void beforeEach(ExtensionContext context) {
        // MongoDB is not automatically cleaned after each Test by Spring, because it does not support
        // Transactions in the same way as a Rational DB. So we have to do is our way:
        // Delete all collections that the test might have created.
        //
        // See also:
        // https://stackoverflow.com/questions/60178310/spring-data-mongodb-transactional-isnt-working
        // https://stackoverflow.com/questions/38354181/spring-boot-mongorepository-rollback-for-tests
        MongoDatabase mongoDatabase = mongoClient.getDatabase(MONGO_DB_NAME);
        mongoDatabase.drop();
    }


    @Override
    public void afterAll(ExtensionContext context) {
        mongoClient.close();
        mongoContainer.close();
    }


}
