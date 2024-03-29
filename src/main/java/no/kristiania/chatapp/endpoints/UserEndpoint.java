package no.kristiania.chatapp.endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import no.kristiania.chatapp.db.User;
import no.kristiania.chatapp.db.UserDao;

import java.sql.SQLException;
import java.util.List;

@Path("/")
public class UserEndpoint {

    @Inject
    private UserDao userDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> retrieveAllUser() throws SQLException {
        return userDao.retrieveAllUsers();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveUser(User user) throws SQLException {
        userDao.save(user);
    }

    @Path("/update_user")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public User updateUser(User user) throws SQLException {
        userDao.updateUser(user);
        return user;
    }
}
