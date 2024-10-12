package habitsTrackApp.model;

import java.util.ArrayList;

public class Admin {
    private Integer id;
    private String name;
    private ArrayList<User> users = new ArrayList<>();

    public Admin(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public Integer getId() {
        return id;
    }
}
