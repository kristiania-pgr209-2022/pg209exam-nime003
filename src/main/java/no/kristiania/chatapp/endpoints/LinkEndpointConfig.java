package no.kristiania.chatapp.endpoints;

import no.kristiania.chatapp.db.MessageDao;
import no.kristiania.chatapp.db.UserGroupLinkDao;
import no.kristiania.chatapp.db.jdbc.JdbcMessageDao;
import no.kristiania.chatapp.db.jdbc.JdbcUserGroupLinkDao;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.sql.DataSource;

public class LinkEndpointConfig extends ResourceConfig {
    public LinkEndpointConfig(DataSource dataSource) {
        super(LinkEndpoint.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(JdbcUserGroupLinkDao.class).to(UserGroupLinkDao.class);
                bindFactory(() -> dataSource).to(DataSource.class);
            }
        });
    }
}
