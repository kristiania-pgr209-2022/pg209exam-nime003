package no.kristiania.chatapp.db.jdbc;

import no.kristiania.chatapp.db.Group;
import no.kristiania.chatapp.db.Message;
import no.kristiania.chatapp.db.User;

import java.util.ArrayList;
import java.util.Random;

public class SampleData {

    private static final Random random = new Random();

    public User sampleUser(){
        var user = new User();
        user.setUsername(pickOne("Nils", "Pål", "Ipsum", "lorem"));
        user.setEmail(pickOne("shemale@gmale.no", "jensarne@yahoo.com", "pålroger@hotmail.kr"));
        user.setPassword(pickOne("sliN", "låP", "Ipsum", "lorem"));
        return user;
    }

    public ArrayList<User> sampleUsers(int howMany){
        var list = new ArrayList<User>();
        for (int i = 0; i < howMany; i++){
            var sampleUser = sampleUser();
            sampleUser.setUsername(sampleUser.getUsername() + i);
            list.add(sampleUser);
        }
        return list;
    }

    public Group sampleGroup(){
        var group = new Group();
        group.setGroupName(pickOne("Klassechat", "chattern", "lorem Ipsum", "amet sit dolor"));
        return group;
    }

    public ArrayList<Group> sampleGroups(int howMany) {
        var list = new ArrayList<Group>();
        for (int i = 0; i < howMany; i++) {
            var sampleGroup = sampleGroup();
            sampleGroup.setGroupName(sampleGroup.getGroupName() + i);
            list.add(sampleGroup);
        }
        return list;
    }

    public Message sampleMessage(Long senderId, Long groupId){
        var message = new Message();
        message.setUserId(senderId);
        message.setGroupId(groupId);
        message.setMessage(pickOne("Hello World!", "World Hello!", "Lorem ipsum", "dolor sit amet"));
        message.setTitle(pickOne("Lorem Ipsum", "Dolor sit amet", "Hallelujah"));
        return message;
    }

    private String pickOne(String... alternatives) {
        return alternatives[random.nextInt(alternatives.length)];
    }
}
