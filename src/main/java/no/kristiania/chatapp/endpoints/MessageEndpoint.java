package no.kristiania.chatapp.endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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

}
