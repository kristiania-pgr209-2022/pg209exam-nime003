package no.kristiania.chatapp.endpoints;

import no.kristiania.chatapp.db.GroupDao;
import no.kristiania.chatapp.db.MessageDao;
import no.kristiania.chatapp.db.jdbc.JdbcGroupDao;
import no.kristiania.chatapp.db.jdbc.JdbcMessageDao;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.sql.DataSource;

public class MessageEndpointConfig extends ResourceConfig {
    public MessageEndpointConfig(DataSource dataSource) {
        super(MessageEndpoint.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(JdbcMessageDao.class).to(MessageDao.class);
                bindFactory(() -> dataSource).to(DataSource.class);
            }
        });
    }
}
