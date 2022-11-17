package no.kristiania.chatapp;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import no.kristiania.chatapp.db.User;

import java.util.ArrayList;

@Path("/user")
public class UserEndpoint {

    private static final ArrayList<User> users = new ArrayList<>();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<User> retrieveAllUser(){
        return users;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveUser(User user){
        users.add(user);
    }
}
