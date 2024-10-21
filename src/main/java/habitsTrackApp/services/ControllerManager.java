package habitsTrackApp.services;

import habitsTrackApp.model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.SortedMap;

/**
 * Класс контроллер для запуска приложения через консоль и
 * взаимодействия с бэкендом приложения через консоль
 *
 * @author Mihail Harhan "mihaillKHn@yandex.ru"
 */

public class ControllerManager {
    private final InMemoryUserManager userManager;
    private final InMemoryHabitsManager habitManager;
    private final InMemoryHistoryManager historyManager;
    private final InMemoryAdminManager adminManager;
    private User user;
    private Habit habit;
    private Admin admin;
    private int daysPassCounter;
    private final DateTimeFormatter onlyDateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public ControllerManager() {
        historyManager = new InMemoryHistoryManager();
        habitManager = new InMemoryHabitsManager(historyManager);
        userManager = new InMemoryUserManager(habitManager, historyManager);
        adminManager = new InMemoryAdminManager(userManager, habitManager);
        user = null;
        habit = null;
        admin = null;
        daysPassCounter = 0;
    }

    public boolean doesUserExists(String email) {
        return userManager.getUserByEmail(email) != null;
    }

    public String registerNewUser(String email, String password) {
        if (doesUserExists(email)) {
            return "Такой пользователь уже есть в системе";
        }
        user = new User(password, email);
        userManager.addNewUser(user);
        user = null;
        return doesUserExists(email) ? "Вы зарегистрированы!" :
                "Некорректный email или пароль или такой пользователь уже есть в системе";
    }

    public String authorizeUser(String email, String password) {
        user = userManager.authorizeUser(email, password);
        if (user == null) {
            return "Некорректный email или пароль";
        } else if (user.isBlocked()) {
            return "Вход не выполнен, вы заблокированы!";
        } else {
            return "Вы вошли в систему";
        }
    }

    public boolean isAuthorized() {
        return user != null;
    }

    public String changeUserName(String newName) {
        userManager.changeName(user, newName);
        return "Новое имя: " + user.getName();
    }

    public String changeUserPassword(String oldPassword, String newPassword) {
        if (!user.getPassword().equals(oldPassword)) {
            return ("Старый пароль неверный");
        }
        userManager.changePassword(user, oldPassword, newPassword);
        return "Новый пароль " + user.getPassword();
    }

    public String changeUserEmail(String newEmail) {
        userManager.changeEmail(user, newEmail);
        if (user.getEmail().equals(newEmail)) {
            return "Новая почта " + user.getEmail();
        } else {
            return "Почта не изменена!";
        }
    }

    public String deleteUser() {
        userManager.deleteUser(user);
        user = null;
        return "Аккаунт удален=(";
    }

    public String logout() {
        user = null;
        return "Вы вышли из системы!";
    }

    public String addNewHabit(String habitName, String description, String typeNumber) {
        HabitType habitType = getHabitTypeByNumber(typeNumber);
        habit = new Habit(habitName, description, habitType, user.getId());
        habitManager.addNewHabit(user, habit);
        habit = null;
        return "done!";
    }

    public void printAllHabits() {
        habitManager.getAllUserHabits(user).forEach(System.out::println);
    }

    public String changeHabitStatus(String statusNumber) {
        if (!statusNumber.equals("1") && !statusNumber.equals("2") && !statusNumber.equals("3")) {
            return "Неверный статус";
        }
        habitManager.updateHabitStatus(user, habit, getHabitStatusByNumber(statusNumber));
        return "done!";
    }

    public String changeHabitName(String habitName) {
        habitManager.updateHabitName(user, habit, habitName);
        return "done!";
    }

    public String changeHabitDescription(String habitDescription) {
        habitManager.updateHabitDescription(user, habit, habitDescription);
        return "done!";
    }

    public boolean doesHabitExists(Integer habitId) {
        return habitManager.getHabitById(user, habitId) != null;
    }

    public String setEveryHabitStatusFinished() {
        habitManager.setEveryHabitStatusFinished(user);
        return "done!";
    }

    public void printAllHabitsWithCertainStatus(String statusId) {
        if (!statusId.equals("1") && !statusId.equals("2") && !statusId.equals("3")) {
            System.out.println("Неверный статус");
            return;
        }
        habitManager.getUserHabitsWithACertainStatus
                (user, getHabitStatusByNumber(statusId)).forEach(System.out::println);
    }

    public void printUserHabitsFilteredByStatus() {
        habitManager.getUserHabitsFilteredByStatus(user).forEach(System.out::println);
    }

    public void printAllUserHabitsFilteredByCreationDate() {
        habitManager.getAllUserHabitsFilteredByCreationDate(user).forEach(System.out::println);
    }

    public String getHabitStatisticForLastNDays(Integer id, int days) {
        habit = habitManager.getHabitById(user, id);
        if (habit == null) {
            return "такой привычки нет";
        }
        if (days > 0 && days <= 30 && daysPassCounter > 0) {
            System.out.println("Статистика за последние " + days + " дней");
            SortedMap<LocalDateTime, HabitStatus> habitMap =
                    historyManager.getHabitStatisticsForGivenPeriod(habit, days);
            for (LocalDateTime date : habitMap.keySet()) {
                System.out.println(date.format(onlyDateFormat) + " - " + habitMap.get(date));
            }
            return "";
        } else if (daysPassCounter == 0) {
            return "Еще не прошел ни 1 день. Начните новый день из меню 5 - Начать новый день";
        } else {
            return "Неверный период";
        }
    }

    public String getSuccessRateHabitStatisticForLastNDays(int days) {
        if (days > 0 && days <= 365 && daysPassCounter > 0) {
            int percent = historyManager.
                    getSuccessPercentOfUsersFinishedHabitsForGivenPeriod(user, days);
            System.out.println("Успешный % выполнения привычек за последние " +
                    days + " дней = " + percent + "%");
            return "";
        } else if (daysPassCounter == 0) {
            return "Еще не прошел ни 1 день. Начните новый день из меню 5 - Начать новый день";
        } else {
            return "неверный период";
        }
    }

    public String deleteHabit(Integer habitId) {
        habitManager.deleteHabitById(user, habitId);
        return "done!";
    }

    public String resetAllDoneDailyHabits() {
        userManager.resetAllDoneDailyHabits(user);
        daysPassCounter++;
        return "done!";
    }

    public String resetAllDoneWeeklyHabits() {
        userManager.resetAllDoneWeeklyHabits(user);
        daysPassCounter++;
        return "done!";
    }

    public String createAdmin(String name) {
        admin = new Admin(name);
        adminManager.createAdmin(admin);
        return "done!";
    }

    public String blockUser(String email) {
        adminManager.blockUser(email);
        return "done!";
    }

    public String deleteUser(String email) {
        adminManager.deleteUser(email);
        return "User deleted!";
    }

    public String unblockUser(String email) {
        adminManager.unblockUser(email);
        return "done!";
    }

    public void showAllUsers() {
        System.out.println(adminManager.getAllUsers());
    }

    public void getAndShowAllUserHabits(String email) {
        adminManager.getAllUserHabits(email).forEach(System.out::println);
    }

    public String logoutAdmin() {
        admin = null;
        return "Вы вышли из админ панели!";
    }

    private HabitType getHabitTypeByNumber(String number) {
        if (number.equals("1")) {
            return HabitType.DAILY;
        } else if (number.equals("2")) {
            return HabitType.WEEKLY;
        } else {
            return HabitType.CUSTOM;
        }
    }

    private HabitStatus getHabitStatusByNumber(String number) {
        if (number.equals("1")) {
            return HabitStatus.NEW;
        } else if (number.equals("2")) {
            return HabitStatus.IN_PROGRESS;
        } else {
            return HabitStatus.FINISHED;
        }
    }
}
