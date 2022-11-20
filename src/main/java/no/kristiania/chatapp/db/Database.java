package no.kristiania.chatapp.db;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Database {
    public static HikariDataSource getDataSource() throws IOException {
        var properties = new Properties();
        try (var reader = new FileReader("application.properties")) {
            properties.load(reader);
        }

        var dataSource = new HikariDataSource();
        dataSource.setUsername(properties.getProperty("dataSource.username", "sa"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));
        dataSource.setJdbcUrl(properties.getProperty("dataSource.url", "jdbc:sqlserver://localhost;encrypt=true;trustServerCertificate=true"));
        dataSource.setConnectionTimeout(5000);
        Flyway.configure().dataSource(dataSource).load().migrate();

        return dataSource;
    }
}
