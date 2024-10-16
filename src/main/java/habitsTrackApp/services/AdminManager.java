package habitsTrackApp.services;

import habitsTrackApp.model.Admin;
import habitsTrackApp.model.Habit;
import habitsTrackApp.model.User;

import java.util.ArrayList;
import java.util.HashMap;

public interface AdminManager {
    void createAdmin(Admin admin);

    void deleteAdmin(int id);

    void blockUser(String email);

    void unblockUser(String email);

    void deleteUser(String email);

    HashMap<String, User> getAllUsers();

    ArrayList<Habit> getAllUserHabits(String email);
}
