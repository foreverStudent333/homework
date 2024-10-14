package habitsTrackApp.controller;

import habitsTrackApp.model.Habit;
import habitsTrackApp.model.HabitStatus;
import habitsTrackApp.model.HabitType;
import habitsTrackApp.model.User;
import habitsTrackApp.services.InMemoryHabitsManager;
import habitsTrackApp.services.InMemoryUserManager;

import java.util.Scanner;

public class ControllerConsole {
    Scanner scanner;
    private InMemoryUserManager userManager;
    private InMemoryHabitsManager habitManager;
    private User user;
    private Habit habit;

    public ControllerConsole() {
        userManager = new InMemoryUserManager();
        habitManager = new InMemoryHabitsManager();
        user = null;
        habit = null;
        scanner = new Scanner(System.in);
    }

    public void printMenuAndDoCommands() {
        printMenu();
        int command = scanner.nextInt();
        String email = "";
        String password = "";

        while (command != 0) {
            switch (command) {
                case 1:
                    System.out.println("Введите email");
                    email = scanner.next();
                    if (userManager.getUserByEmail(email) != null) {
                        System.out.println("Такой пользователь уже есть");
                        break;
                    }
                    System.out.println("Введите пароль (не больше 50 символов)");
                    password = scanner.next();
                    user = new User(password, email);
                    userManager.addNewUser(user);
                    if (userManager.getUserByEmail(email) != null) {
                        System.out.println("Вы зарегистрированы!");
                    } else {
                        System.out.println("Некорректный email или пароль");
                    }
                    user = null;
                    break;
                case 2:
                    System.out.println("Введите email");
                    email = scanner.next();
                    if (userManager.getUserByEmail(email) == null) {
                        System.out.println("Такого пользователя нет");
                        break;
                    }
                    System.out.println("Введите пароль (не больше 50 символов)");
                    password = scanner.next();
                    if (userManager.getUserByEmail(email).getPassword().equals(password)) {
                        System.out.println("Вы вошли в систему");
                        user = userManager.getUserByEmail(email);
                    } else {
                        System.out.println("Некорректный email или пароль");
                    }

                    break;
                case 3:
                    if (user == null) {
                        System.out.println("Ты не авторизирован в системе");
                        break;
                    }
                    printMenuForUserUpdate();
                    command = scanner.nextInt();

                    switch (command) {
                        case 1:
                            System.out.println("Введи новое имя");
                            String name = scanner.next();
                            userManager.changeName(user, name);
                            System.out.println("Новое имя: " + user.getName());
                            break;
                        case 2:
                            System.out.println("Введи старый пароль");
                            String oldPas = scanner.next();
                            System.out.println("Введи новый пароль");
                            String newPas = scanner.next();
                            if (!user.getPassword().equals(oldPas)) {
                                System.out.println("Старый пароль неверный");
                                break;
                            }
                            userManager.changePassword(user, oldPas, newPas);
                            System.out.println("Новый пароль " + user.getPassword());
                            break;
                        case 3:
                            System.out.println("Введи новую почту");
                            String newEmail = scanner.next();
                            userManager.changeEmail(user, newEmail);
                            if (user.getEmail().equals(newEmail)) {
                                System.out.println("Новый пароль " + user.getEmail());
                            } else {
                                System.out.println("Пароль не изменен. Некорректный пароль или пароль" +
                                        "уже есть в системе");
                            }
                            break;
                        case 4:
                            userManager.deleteUser(user);
                            user = null;
                            System.out.println("Аккаунт удален=(");
                            break;
                        case 5:
                            user = null;
                            System.out.println("Вы вышли из системы!");
                            break;
                        case 0:
                            break;
                        default:
                            System.out.println("Такой команды нет, введите команду снова.");
                            break;
                    }
                    break;
                case 4:
                    if (user == null) {
                        System.out.println("Ты не авторизирован в системе");
                        break;
                    }
                    printMenuForHabitsUpdate();
                    command = scanner.nextInt();
                    String name = "";
                    String description = "";
                    int id = 0;
                    String type = "";
                    String status = "";
                    HabitStatus habitStatus = HabitStatus.NEW;

                    switch (command) {
                        case 1:
                            System.out.println("Введите название привычки");
                            name = scanner.next();
                            System.out.println("Введите описание привычки");
                            description = scanner.next();
                            System.out.println("Выберите тип привычки по номеру (введите номер): " +
                                    "1 - daily, 2 - weekly, 3 - any");
                            type = scanner.next();
                            HabitType habitType = getHabitTypeByNumber(type);
                            habit = new Habit(name, description, habitType);
                            habitManager.addNewHabit(user, habit);
                            System.out.println("done");
                            habit = null;
                            break;
                        case 2:
                            habitManager.getAllUserHabits(user).forEach(System.out::println);
                            break;
                        case 3:
                            System.out.println("Введите id привычки для изменения");
                            id = scanner.nextInt();
                            habit = habitManager.getHabitById(user, id);
                            System.out.println("Если что-либо менять не требуется введите 0\n " +
                                    "Введите новое название");
                            name = scanner.next();
                            if (!name.equals("0")) {
                                habitManager.updateHabitName(user, habit, name);
                            }
                            System.out.println("Введите новое описание");
                            description = scanner.next();
                            if (!description.equals("0")) {
                                habitManager.updateHabitDescription(user, habit, description);
                            }
                            System.out.println("Введите новый статус привычки по номеру (введите номер): " +
                                    "1 - new, 2 - in_progress, 3 - finished");
                            status = scanner.next();
                            habitStatus = getHabitStatusByNumber(status);
                            habitManager.updateHabitStatus(user, habit, habitStatus);
                            System.out.println("done!");
                            break;
                        case 4:
                            System.out.println("С каким статусом показать все привычки (введите номер): " +
                                    "1 - new, 2 - in_progress, 3 - finished");
                            status = scanner.next();
                            habitStatus = getHabitStatusByNumber(status);
                            habitManager.getUserHabitsWithACertainStatus(user, habitStatus).forEach(System.out::println);
                            break;
                        case 5:
                            habitManager.getUserHabitsFilteredByStatus(user).forEach(System.out::println);
                            break;
                        case 6:
                            habitManager.getAllUserHabitsFilteredByCreationDate(user).forEach(System.out::println);
                            break;
                        case 7:
                            System.out.println("Введите id привычки для удаления");
                            id = scanner.nextInt();
                            habitManager.deleteHabitById(user, id);
                            break;
                        case 8:
                            user = null;
                            System.out.println("Вы вышли из системы!");
                            break;
                        case 0:
                            break;
                        default:
                            System.out.println("Такой команды нет, введите команду снова.");
                            break;
                    }
                    break;
                default:
                    System.out.println("Такой команды нет, введите команду снова.");
                    break;
            }
            printMenu();
            command = scanner.nextInt();
        }
    }


    private void printMenu() {
        System.out.println("Привет!=) Что хочешь сделать?");
        System.out.println("1 - Регистрация");
        System.out.println("2 - Авторизация");
        System.out.println("3 - Редактировать профиль пользователя");
        System.out.println("4 - Редактировать привычки пользователя");
        System.out.println("0 - Выйти из приложения.");
    }

    private void printMenuForUserUpdate() {
        System.out.println("Выбери команду ниже");
        System.out.println("1 - Поменять имя");
        System.out.println("2 - Поменять пароль");
        System.out.println("3 - Поменять почту");
        System.out.println("4 - Удалить аккаунт");
        System.out.println("5 - Выйти из аккаунта");
        System.out.println("0 - Назад");
    }

    private void printMenuForHabitsUpdate() {
        System.out.println("Выбери команду ниже");
        System.out.println("1 - Создать привычку");
        System.out.println("2 - Показать все привычки");
        System.out.println("3 - Поменять привычку");
        System.out.println("4 - Показать все привычки определенного статуса выполнения");
        System.out.println("5 - Показать все привычки отсортированные по статусу выполнения");
        System.out.println("6 - Показать все привычки отсортированные по дате создания");
        System.out.println("7 - Удалить привычку");
        System.out.println("8 - Выйти из аккаунта");
        System.out.println("0 - Назад");
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
