package schwarz.it.ae.bookmarx;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

public class PostgresSharedTestContainerExtension implements BeforeAllCallback, BeforeEachCallback, ExtensionContext.Store.CloseableResource {


    private static final PostgreSQLContainer<?> postgresContainer;

    private static Connection conn = null;
    private static Statement stmt = null;



    // Run once for ALL Tests
    static {
        postgresContainer = new PostgreSQLContainer(
                DockerImageName.parse("postgres:13.1")
        ).withDatabaseName("integration-tests-db")
                .withUsername("sa")
                .withPassword("sa");

        postgresContainer.start();
        System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresContainer.getPassword());
    }




    private static boolean started = false;
    // Gatekeeper to prevent multiple Threads within the same routine
    final static Lock lock = new ReentrantLock();

    @Override
    public void beforeAll(final ExtensionContext context) throws Exception {
        // lock the access so only one Thread has access to it
        lock.lock();
        if (!started) {
            started = true;
            // Your "before all tests" startup logic goes here
            // The following line registers a callback hook when the root test context is
            // shut down
            context.getRoot().getStore(GLOBAL).put("any unique name", this);

            // do your work - which might take some time -
            // or just uses more time than the simple check of a boolean
            Properties props = new Properties();
            props.setProperty("user", postgresContainer.getUsername());
            props.setProperty("password", postgresContainer.getPassword());
            conn = DriverManager.getConnection(postgresContainer.getJdbcUrl(), props);
            System.out.println("### " + postgresContainer.getJdbcUrl() + "###");
            stmt = conn.createStatement();

        }
        // free the access
        lock.unlock();
    }

    @Override
    public void close() {
        // Your "after all tests" logic goes here
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            // Do nothing here
        }

    }




    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        stmt.execute("Delete From bmx_folder_bookmark_assignment");
        stmt.execute("Delete From bmx_folder");
        stmt.execute("Delete From bmx_bookmark");
    }
}
