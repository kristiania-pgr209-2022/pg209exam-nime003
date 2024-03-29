package no.kristiania.chatapp.endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import no.kristiania.chatapp.db.Group;
import no.kristiania.chatapp.db.Message;
import no.kristiania.chatapp.db.MessageDao;

import java.sql.SQLException;
import java.util.List;

@Path("/")
public class MessageEndpoint {
    @Inject
    private MessageDao messageDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> retrieveAllMessages() throws SQLException {
        return messageDao.retrieveAllMessages();
    }

    @Path("/{groupId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> retrieveAllGroupsByUser(@PathParam("groupId")long groupId) throws SQLException {
        return messageDao.retrieveAllMessagesByGroupId(groupId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void save(Message message) throws SQLException {
        messageDao.save(message);
    }
}
