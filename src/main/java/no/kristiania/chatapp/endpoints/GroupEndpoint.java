package no.kristiania.chatapp.endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import no.kristiania.chatapp.db.Group;
import no.kristiania.chatapp.db.GroupDao;

import java.sql.SQLException;
import java.util.List;

@Path("/")
public class GroupEndpoint {
    @Inject
    private GroupDao groupDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Group> retrieveAllGroups() throws SQLException {
        return groupDao.retrieveAllGroups();
    }

    @Path("/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Group> retrieveAllGroupsByUser(@PathParam("userId")long userId) throws SQLException {
        return groupDao.retrieveGroupByUserId(userId);
    }
}
