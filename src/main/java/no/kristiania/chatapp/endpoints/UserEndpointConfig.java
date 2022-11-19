package no.kristiania.chatapp.endpoints;

import no.kristiania.chatapp.db.UserDao;
import no.kristiania.chatapp.db.jdbc.JdbcUserDao;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.sql.DataSource;

public class UserEndpointConfig extends ResourceConfig {
    public UserEndpointConfig(DataSource dataSource) {
        super(UserEndpoint.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(JdbcUserDao.class).to(UserDao.class);
                bindFactory(() -> dataSource).to(DataSource.class);
            }
        });
    }

}
