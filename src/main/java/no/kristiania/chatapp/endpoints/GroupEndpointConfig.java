package no.kristiania.chatapp.endpoints;

import no.kristiania.chatapp.db.GroupDao;
import no.kristiania.chatapp.db.UserDao;
import no.kristiania.chatapp.db.jdbc.JdbcGroupDao;
import no.kristiania.chatapp.db.jdbc.JdbcUserDao;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.sql.DataSource;

public class GroupEndpointConfig extends ResourceConfig {
    public GroupEndpointConfig(DataSource dataSource) {
        super(GroupEndpoint.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(JdbcGroupDao.class).to(GroupDao.class);
                bindFactory(() -> dataSource).to(DataSource.class);
            }
        });
    }
}
