package no.kristiania.chatapp;

import no.kristiania.chatapp.db.UserDao;
import no.kristiania.chatapp.db.jdbc.JdbcUserDao;
import no.kristiania.chatapp.endpoints.UserEndpoint;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.sql.DataSource;

public class ChatappConfig extends ResourceConfig {
    public ChatappConfig(DataSource dataSource) {
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
