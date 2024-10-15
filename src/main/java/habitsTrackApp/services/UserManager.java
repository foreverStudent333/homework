package habitsTrackApp.services;

import habitsTrackApp.model.Habit;
import habitsTrackApp.model.HabitStatus;
import habitsTrackApp.model.User;

public interface UserManager {
    void addNewUser(User user);

    User getUserByEmail(String email);

    User authorizeUser(String email, String password);

    void deleteUser(User user);

    void changePassword(User user, String oldPassword, String newPassword);

    void changeName(User user, String newName);

    void changeEmail(User user, String newEmail);

    void resetAllDoneDailyHabits (User user);

    void resetAllDoneWeeklyHabits (User user);

    void changePasswordByEmailRecoveryCode(User user, String newPassword);
}
