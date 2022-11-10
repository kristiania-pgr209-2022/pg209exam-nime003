package no.kristiania.chatapp.db;

public class Group {
    private int id;
    private String groupName;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String s) {
        this.groupName = s;
    }
}
