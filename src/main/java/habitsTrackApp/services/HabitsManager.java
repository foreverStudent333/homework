package habitsTrackApp.services;

import habitsTrackApp.model.Habit;
import habitsTrackApp.model.HabitStatus;
import habitsTrackApp.model.User;

import java.util.ArrayList;

public interface HabitsManager {
    void addNewHabit(User user, Habit habit);

    void deleteHabitById(User user, Integer id);

    void updateHabitName(User user, Habit habit, String newName);

    void updateHabitDescription(User user, Habit habit, String newDescription);

    void updateHabitStatus(User user, Habit habit, HabitStatus newStatus);

    Habit getHabitById(User user, int habitId);

    ArrayList<Habit> getAllUserHabits(User user);

    ArrayList<Habit> getUserHabitsWithACertainStatus(User user, HabitStatus status);

    ArrayList<Habit> getUserHabitsFilteredByStatus(User user);

    ArrayList<Habit> getAllUserHabitsFilteredByCreationDate(User user);
}
