package no.kristiania.chatapp.endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import no.kristiania.chatapp.db.Group;
import no.kristiania.chatapp.db.GroupDao;
import no.kristiania.chatapp.db.User;

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

    @Path("{groupId}/users")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> retrieveAllUsers(@PathParam("groupId")long groupId) throws SQLException {
        return groupDao.retrieveAllUsers(groupId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Group addGroup(Group group) throws SQLException {
        groupDao.save(group);
        return group;
    }
}
