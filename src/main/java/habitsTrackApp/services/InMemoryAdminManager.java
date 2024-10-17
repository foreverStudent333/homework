package habitsTrackApp.services;

import habitsTrackApp.model.Admin;
import habitsTrackApp.model.Habit;
import habitsTrackApp.model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryAdminManager implements AdminManager {
    private final InMemoryUserManager.IdGenerator adminIdGenerator;
    final private HashMap<Integer, Admin> adminsById;
    final InMemoryUserManager inMemoryUserManager;
    final InMemoryHabitsManager inMemoryHabitsManager;

    public InMemoryAdminManager() {
        adminIdGenerator = new InMemoryUserManager.IdGenerator();
        adminsById = new HashMap<>();
        inMemoryHabitsManager = new InMemoryHabitsManager();
        inMemoryUserManager = new InMemoryUserManager();
    }

    public InMemoryAdminManager(InMemoryUserManager inMemoryUserManager, InMemoryHabitsManager inMemoryHabitsManager) {
        this.inMemoryUserManager = inMemoryUserManager;
        this.inMemoryHabitsManager = inMemoryHabitsManager;
        adminIdGenerator = new InMemoryUserManager.IdGenerator();
        adminsById = new HashMap<>();
    }

    @Override
    public void createAdmin(Admin admin) {
        admin.setId(adminIdGenerator.getNextFreeId());
        adminsById.put(admin.getId(), admin);
    }

    @Override
    public void deleteAdmin(int id) {
        adminsById.remove(id);
    }

    @Override
    public void blockUser(String email) {
        User user = inMemoryUserManager.getUserByEmail(email);
        if(user != null) {
            user.setBlocked(true);
        }
    }

    @Override
    public void unblockUser(String email) {
        User user = inMemoryUserManager.getUserByEmail(email);
        if(user != null) {
            user.setBlocked(false);
        }
    }

    @Override
    public void deleteUser(String email) {
        inMemoryUserManager.deleteUser(inMemoryUserManager.getUserByEmail(email));
    }

    @Override
    public HashMap<String, User> getAllUsers() {
        return inMemoryUserManager.getUsers();
    }

    @Override
    public ArrayList<Habit> getAllUserHabits(String email) {
        return inMemoryHabitsManager.getAllUserHabits(inMemoryUserManager.getUserByEmail(email));
    }

    public HashMap<Integer, Admin> getAdminsById() {
        return adminsById;
    }
}
