package no.kristiania.chatapp.endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import no.kristiania.chatapp.db.Group;
import no.kristiania.chatapp.db.GroupDao;
import no.kristiania.chatapp.db.UserGroupLink;
import no.kristiania.chatapp.db.UserGroupLinkDao;

import java.sql.SQLException;
import java.util.List;

@Path("/")
public class LinkEndpoint {
    @Inject
    private UserGroupLinkDao linkDao;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public UserGroupLink save(UserGroupLink link) throws SQLException {
        linkDao.save(link);
        return link;
    }
}
