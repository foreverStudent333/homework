package habitsTrackApp.services;

import habitsTrackApp.model.Habit;
import habitsTrackApp.model.HabitsStatus;
import habitsTrackApp.model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHabitsManager implements HabitsManager{
    private final InMemoryUserManager.UserIdGenerator userIdGenerator;
    final HashMap<User, HashMap<Integer,Habit>> habitsByUsers;

    public InMemoryHabitsManager() {
        userIdGenerator = new InMemoryUserManager.UserIdGenerator();
        habitsByUsers = new HashMap<>();
    }

    @Override
    public void addNewHabit(User user, Habit habit) {
        habit.setId(userIdGenerator.getNextFreeId());     // выдаю привычке уникальный id
        if (!habitsByUsers.containsKey(user)) {
            habitsByUsers.put(user, new HashMap<>());
            habitsByUsers.get(user).put(habit.getId(), habit);
        } else {
            habitsByUsers.get(user).put(habit.getId(), habit);
        }
    }

    @Override
    public void deleteHabit(User user, Habit habit) {
        if (!habitsByUsers.containsKey(user)) {
            return;
        }
        habitsByUsers.get(user).remove(habit.getId());
    }

    @Override
    public void updateHabitName(User user, Habit habit, String newName) {
        if (!habitsByUsers.containsKey(user)) {
            return;
        }
        habitsByUsers.get(user).get(habit.getId()).setName(newName);
    }

    @Override
    public void updateHabitDescription(User user, Habit habit, String newDescription) {
        if (!habitsByUsers.containsKey(user)) {
            return;
        }
        habitsByUsers.get(user).get(habit.getId()).setDescription(newDescription);
    }

    @Override
    public void updateHabitStatus(User user, Habit habit, HabitsStatus newStatus) {
        if (!habitsByUsers.containsKey(user)) {
            return;
        }
    }

    public HashMap<User, HashMap<Integer, Habit>> getHabitsByUsers() {
        return habitsByUsers;
    }

    public static final class UserIdGenerator {
        private int nextFreeId = 1;

        public int getNextFreeId() {
            return nextFreeId++;
        }
    }
}
