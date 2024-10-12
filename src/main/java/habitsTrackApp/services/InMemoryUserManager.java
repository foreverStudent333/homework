package habitsTrackApp.services;

import habitsTrackApp.model.Habit;
import habitsTrackApp.model.HabitsStatus;
import habitsTrackApp.model.User;

import java.util.HashMap;
import java.util.regex.Pattern;

public class InMemoryUserManager implements UserManager {
    private final UserIdGenerator userIdGenerator;
    final HashMap<String, User> users;
    final InMemoryHabitsManager inMemoryHabitsManager;

    public InMemoryUserManager() {
        userIdGenerator = new UserIdGenerator();
        users = new HashMap<>();
        inMemoryHabitsManager = new InMemoryHabitsManager();
    }

    @Override
    public void addNewUser(User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        if (users.containsKey(email) || password.length() > 25) {
            return;
        }
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(email).matches()) { // простая проверка на корректный емейл
            return;
        }

        user.setId(userIdGenerator.getNextFreeId()); //присваеваю всем юзер айди для внутренней системы(не видно юзерам)
        users.put(email, user);
    }

    @Override
    public User getUserByEmail(String email) {
        return users.get(email);
    }

    @Override
    public User authorizeUser(String email, String password) {
        User user = getUserByEmail(email);
        if (user == null) {
            return null;
        }
        if (user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user.getEmail());
    }

    @Override
    public void changePassword(User user, String oldPassword, String newPassword) {
        User oldUser = getUserByEmail(user.getEmail());
        if (oldUser == null) {
            return;
        }
        if (oldUser.getPassword().equals(newPassword)) {
            oldUser.setPassword(newPassword);
        }
    }

    @Override
    public void changeName(User user, String newName) {
        User oldUser = getUserByEmail(user.getEmail());
        if (oldUser == null) {
            return;
        }
        oldUser.setName(newName);
    }

    @Override
    public void changeEmail(User user, String newEmail) {
        User oldUser = getUserByEmail(user.getEmail());
        User newUser = getUserByEmail(user.getEmail());
        newUser.setEmail(newEmail);
        if (oldUser == null || users.containsKey(newEmail)) {
            return;
        }
        addNewUser(newUser);
        if(users.containsKey(newEmail)) {
            deleteUser(oldUser);
        }
    }

    @Override
    public void changePasswordByEmailRecoveryCode(User user, String newPassword) {
        //
    }

    @Override
    public void addUserHabit(User user, Habit habit) {

    }

    @Override
    public void deleteUserHabit(User user, Habit habit) {

    }

    @Override
    public void updateUserHabitName(User user, Habit habit, String newName) {

    }

    @Override
    public void updateUserHabitDescription(User user, Habit habit, String newDescription) {

    }

    @Override
    public void updateUserHabitStatus(User user, Habit habit, HabitsStatus newStatus) {

    }


    public static final class UserIdGenerator {
        private int nextFreeId = 1;

        public int getNextFreeId() {
            return nextFreeId++;
        }
    }
}
