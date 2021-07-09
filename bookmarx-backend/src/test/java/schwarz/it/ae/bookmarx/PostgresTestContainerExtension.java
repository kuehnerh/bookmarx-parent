package schwarz.it.ae.bookmarx;

import com.mongodb.ClientSessionOptions;
import com.mongodb.client.*;
import com.mongodb.connection.ClusterDescription;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Set;

public class PostgresTestContainerExtension implements BeforeAllCallback, AfterAllCallback {

    private PostgreSQLContainer postgresContainer;



    @Override
    public void beforeAll(ExtensionContext context) {
        postgresContainer = new PostgreSQLContainer(
                DockerImageName.parse("postgres:13.1")
        ).withDatabaseName("integration-tests-db")
                .withUsername("sa")
                .withPassword("sa");

        postgresContainer.start();
        String jdbcUrl = String.format("jdbc:postgresql://localhost:%d/prop", postgresContainer.getFirstMappedPort());
        System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresContainer.getPassword());
    }




    @Override
    public void afterAll(ExtensionContext context) {
        postgresContainer.stop();
    }


}
