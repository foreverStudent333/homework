package habitsTrackApp.services;

import habitsTrackApp.model.Habit;
import habitsTrackApp.model.HabitsStatus;
import habitsTrackApp.model.User;

public interface UserManager {
    void addNewUser(User user);

    User getUserByEmail(String email);

    User authorizeUser(String email, String password);

    void deleteUser(User user);

    void changePassword(User user, String oldPassword, String newPassword);

    void changeName(User user, String newName);

    void changeEmail(User user, String newEmail);

    void changePasswordByEmailRecoveryCode(User user, String newPassword);

    void addUserHabit(User user, Habit habit);

    void deleteUserHabit(User user, Habit habit);

    void updateUserHabitName(User user, Habit habit, String newName);

    void updateUserHabitDescription(User user, Habit habit, String newDescription);

    void updateUserHabitStatus(User user, Habit habit, HabitsStatus newStatus);
}
