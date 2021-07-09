package schwarz.it.ae.bookmarx;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Set;

@Testcontainers
public class AbstractTestcontainerIT {

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer(
            DockerImageName.parse("postgres:13.1")
    ).withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");



    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        System.out.println("###" + postgreSQLContainer.getJdbcUrl());
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }



    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(
            DockerImageName.parse("mongo:4.4.7-focal")
    );

    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        // getReplicaSetUrl() returns something like this: mongodb://localhost:55009/test
        // the port is dynamically assigned, so there should be no conflict with a running dev-database
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    MongoTemplate mongoTemplate;



    @AfterEach
    public void doAfterEachTest() {
        // MongoDB is not automatically cleaned after each Test by Spring, because it does not support
        // Transactions in the same way as a Rational DB. So we have to do is our way:
        // Delete all collections that the test might have created.
        //
        // See also:
        // https://stackoverflow.com/questions/60178310/spring-data-mongodb-transactional-isnt-working
        // https://stackoverflow.com/questions/38354181/spring-boot-mongorepository-rollback-for-tests
        Set<String> collectionsNames = mongoTemplate.getCollectionNames();
        for (String col : collectionsNames) {
            mongoTemplate.dropCollection(col);
        }
    }
}
