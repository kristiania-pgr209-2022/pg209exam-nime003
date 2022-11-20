package no.kristiania.chatapp.db;

public class Group {
    private long id;
    private String groupName;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() { return id; }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String s) {
        this.groupName = s;
    }
}
