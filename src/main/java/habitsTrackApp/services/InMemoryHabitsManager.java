package habitsTrackApp.services;

import habitsTrackApp.model.Habit;
import habitsTrackApp.model.HabitStatus;
import habitsTrackApp.model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Класс описывающий логику работы с классом {@link Habit} для работы консольного приложения.
 * Включает в себе реализацию crud для привычек и хранения всех привычек юзеров в формате
 * ключ(юзер) - значение(мапа всех привычек юзера)
 *
 * @author Mihail Harhan "mihaillKHn@yandex.ru"
 */
public class InMemoryHabitsManager implements HabitsManager {
    private final InMemoryUserManager.IdGenerator habitIdGenerator;
    private final HashMap<User, HashMap<Integer, Habit>> habitsByUsers;
    final InMemoryHistoryManager inMemoryHistoryManager;

    public InMemoryHabitsManager() {
        habitIdGenerator = new InMemoryUserManager.IdGenerator();
        habitsByUsers = new HashMap<>();
        inMemoryHistoryManager = new InMemoryHistoryManager();
    }

    /**
     * Создает экземпляр класса с переданным экземпляром {@code inMemoryHistoryManager}
     *
     * @param inMemoryHistoryManager который хранит внутри себя все данные и логику
     *                               для работы с историей выполнения привычек
     */
    public InMemoryHabitsManager(InMemoryHistoryManager inMemoryHistoryManager) {
        this.inMemoryHistoryManager = inMemoryHistoryManager;
        habitIdGenerator = new InMemoryUserManager.IdGenerator();
        habitsByUsers = new HashMap<>();
    }

    @Override
    public void addNewHabit(User user, Habit habit) {
        habit.setId(habitIdGenerator.getNextFreeId());     // выдаю привычке уникальный id
        if (!habitsByUsers.containsKey(user)) {
            habitsByUsers.put(user, new HashMap<>());
            habitsByUsers.get(user).put(habit.getId(), habit);
        } else {
            habitsByUsers.get(user).put(habit.getId(), habit);
        }
        inMemoryHistoryManager.createHabitHistory(habit);
    }

    @Override
    public void deleteHabitById(User user, Integer id) {
        if (!habitsByUsers.containsKey(user)) {
            return;
        }
        inMemoryHistoryManager.deleteHabitFromHistory(habitsByUsers.get(user).get(id));
        habitsByUsers.get(user).remove(id);
    }

    @Override
    public void updateHabitName(User user, Habit habit, String newName) {
        if (!habitsByUsers.containsKey(user) || !habitsByUsers.get(user).containsKey(habit.getId())) {
            return;
        }
        habitsByUsers.get(user).get(habit.getId()).setName(newName);
    }

    @Override
    public void updateHabitDescription(User user, Habit habit, String newDescription) {
        if (!habitsByUsers.containsKey(user) || !habitsByUsers.get(user).containsKey(habit.getId())) {
            return;
        }
        habitsByUsers.get(user).get(habit.getId()).setDescription(newDescription);
    }

    @Override
    public void updateHabitStatus(User user, Habit habit, HabitStatus newStatus) {
        if (!habitsByUsers.containsKey(user) || !habitsByUsers.get(user).containsKey(habit.getId())) {
            return;
        }
        habitsByUsers.get(user).get(habit.getId()).setHabitStatus(newStatus);
    }

    @Override
    public void setEveryHabitStatusFinished(User user) {
        getAllUserHabits(user).forEach(habit -> habit.setHabitStatus(HabitStatus.FINISHED));
    }

    public HashMap<User, HashMap<Integer, Habit>> getHabitsByUsers() {
        return habitsByUsers;
    }

    @Override
    public Habit getHabitById(User user, int habitId) {
        if (!habitsByUsers.containsKey(user)) {
            return null;
        }
        return habitsByUsers.get(user).get(habitId);
    }

    @Override
    public ArrayList<Habit> getAllUserHabits(User user) {
        if (!habitsByUsers.containsKey(user)) {
            return null;
        }
        return new ArrayList<>(habitsByUsers.get(user).values());
    }

    @Override
    public ArrayList<Habit> getUserHabitsWithACertainStatus(User user, HabitStatus status) {
        if (!habitsByUsers.containsKey(user)) {
            return null;
        }
        ArrayList<Habit> allHabits = getAllUserHabits(user);
        return allHabits.stream().filter(e -> e.getHabitStatus().equals(status))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Habit> getUserHabitsFilteredByStatus(User user) {
        if (!habitsByUsers.containsKey(user)) {
            return null;
        }
        ArrayList<Habit> habits = getAllUserHabits(user);
        habits.sort(Comparator.comparing(Habit::getHabitStatus));
        return habits;
    }

    @Override
    public ArrayList<Habit> getAllUserHabitsFilteredByCreationDate(User user) {
        if (!habitsByUsers.containsKey(user)) {
            return null;
        }
        ArrayList<Habit> habits = getAllUserHabits(user);
        habits.sort(Comparator.comparing(Habit::getStartDate));
        return habits;
    }
}
