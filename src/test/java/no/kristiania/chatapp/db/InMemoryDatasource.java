package no.kristiania.chatapp.db;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class InMemoryDatasource {
    public static DataSource createTestDataSource() {
        var dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:DaoTest;DB_CLOSE_DELAY=-1;MODE=MSSQLServer");
        var flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
        return dataSource;
    }
}
