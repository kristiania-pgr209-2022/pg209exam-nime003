package no.kristiania.chatapp.db.jdbc;

import no.kristiania.chatapp.db.Group;
import no.kristiania.chatapp.db.Message;
import no.kristiania.chatapp.db.User;

import java.util.Random;

public class SampleData {

    private static final Random random = new Random();

    public User sampleUser(){
        var user = new User();
        user.setUsername(pickOne("Nils", "Pål"));
        user.setPassword(pickOne("sliN", "låP"));
        return user;
    }

    public Group sampleGroup(){
        var group = new Group();
        group.setGroupName(pickOne("Klassechat", "chattern", "Ipsum lorem", "amet sit dolor"));
        return group;
    }

    public Message sampleMessage(Long senderId, Long groupId){
        var message = new Message();
        message.setSenderId(senderId);
        message.setGroupId(groupId);
        message.setMessage(pickOne("Hello World!", "World Hello!", "Lorem ipsum", "dolor sit amet"));
        return message;
    }

    private String pickOne(String... alternatives) {
        return alternatives[random.nextInt(alternatives.length)];
    }
}
