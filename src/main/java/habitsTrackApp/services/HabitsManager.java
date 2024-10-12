package habitsTrackApp.services;

import habitsTrackApp.model.Habit;
import habitsTrackApp.model.HabitsStatus;
import habitsTrackApp.model.User;

public interface HabitsManager {
    void addNewHabit(User user, Habit habit);

    void deleteHabit(User user, Habit habit);

    void updateHabitName(User user, Habit habit, String newName);

    void updateHabitDescription(User user, Habit habit, String newDescription);

    void updateHabitStatus(User user, Habit habit, HabitsStatus newStatus);
}
