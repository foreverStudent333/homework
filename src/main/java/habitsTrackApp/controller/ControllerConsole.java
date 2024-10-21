package habitsTrackApp.controller;

import habitsTrackApp.services.ControllerManager;
import habitsTrackApp.util.Constants;

import java.util.Scanner;

/**
 * Класс контроллер для запуска приложения через консоль и
 * взаимодействия с бэкендом приложения через консоль
 *
 * @author Mihail Harhan "mihaillKHn@yandex.ru"
 */

public class ControllerConsole {
    Scanner scanner;
    private final ControllerManager controller;

    public ControllerConsole() {
        controller = new ControllerManager();
        scanner = new Scanner(System.in);
    }

    public void printMenuAndDoCommands() {
        printMenu();
        int command = scanner.nextInt();
        String email;
        String password;

        while (command != 0) {
            switch (command) {
                case 1 -> {
                    print("Введите email");
                    email = scanner.next();
                    print("Введите пароль (не больше 50 символов)");
                    password = scanner.next();
                    print(controller.registerNewUser(email, password));
                }
                case 2 -> {
                    print("Введите email");
                    email = scanner.next();
                    print("Введите пароль (не больше 50 символов)");
                    password = scanner.next();
                    print(controller.authorizeUser(email, password));
                }
                case 3 -> doCommandsForUserMenu();
                case 4 -> doCommandsForHabitMenu();
                case 5 -> print(controller.resetAllDoneDailyHabits());
                case 6 -> print(controller.resetAllDoneWeeklyHabits());
                case 7 -> doCommandsForAdminMenu();
                default -> print("Такой команды нет, введите команду снова.");
            }
            printMenu();
            command = scanner.nextInt();
        }
    }

    private void printMenu() {
        System.out.println(Constants.PRINT_MENU);
    }

    private void printMenuForUserUpdate() {
        System.out.println(Constants.PRINT_MENU_FOR_USER_UPDATE);
    }

    private void printMenuForHabitsUpdateAndStatistics() {
        System.out.println(Constants.PRINT_MENU_FOR_HABITS_UPDATE_AND_STATISTICS);
    }

    private void printMenuForAdmin() {
        System.out.println(Constants.PRINT_MENU_FOR_ADMIN);
    }

    private void print(String message) {
        System.out.println(message);
    }

    private void doCommandsForUserMenu() {
        if (!controller.isAuthorized()) {
            print("Ты не авторизирован в системе");
            return;
        }
        printMenuForUserUpdate();
        int command = scanner.nextInt();

        switch (command) {
            case 1 -> {
                print("Введи новое имя");
                print(controller.changeUserName(scanner.next()));
            }
            case 2 -> {
                print("Введи старый пароль");
                String oldPas = scanner.next();
                print("Введи новый пароль");
                print(controller.changeUserPassword(oldPas, scanner.next()));
            }
            case 3 -> {
                print("Введи новую почту");
                print(controller.changeUserEmail(scanner.next()));
            }
            case 4 -> print(controller.deleteUser());
            case 5 -> print(controller.logout());
            case 0 -> print("Вы вышли в меню");
            default -> print("Такой команды нет, введите команду снова.");
        }
    }

    private void doCommandsForHabitMenu() {
        if (!controller.isAuthorized()) {
            print("Ты не авторизирован в системе");
            return;
        }
        printMenuForHabitsUpdateAndStatistics();
        int command = scanner.nextInt();
        int id;

        switch (command) {
            case 1 -> {
                print("Введите название привычки");
                String habitName = scanner.next();
                print("Введите описание привычки");
                String description = scanner.next();
                print("Выберите тип привычки по номеру (введите номер): " +
                        "1 - daily, 2 - weekly, 3 - any");
                print(controller.addNewHabit(habitName, description, scanner.next()));
            }
            case 2 -> controller.printAllHabits();
            case 3 -> {
                print("Введите id привычки для изменения");
                id = scanner.nextInt();
                if (!controller.doesHabitExists(id)) {
                    print("такой привычки нет");
                    break;
                }
                print("""
                        Если хотите поменять статус введите 1
                        Если хотите поменять название введите 2
                        Если хотите поменять описание введите 3""");
                int option = scanner.nextInt();
                switch (option) {
                    case 1 -> {
                        print("Введите новый статус привычки по номеру (введите номер): " +
                                "1 - new, 2 - in_progress, 3 - finished");
                        print(controller.changeHabitStatus(scanner.next()));
                    }
                    case 2 -> {
                        print("Введите новое название");
                        print(controller.changeHabitName(scanner.next()));
                    }
                    case 3 -> {
                        print("Введите новое описание");
                        print(controller.changeHabitDescription(scanner.next()));
                    }
                    default -> print("Такой команды нет");
                }
            }
            case 4 -> print(controller.setEveryHabitStatusFinished());
            case 5 -> {
                print("С каким статусом показать все привычки (введите номер): " +
                        "1 - new, 2 - in_progress, 3 - finished");
                controller.printAllHabitsWithCertainStatus(scanner.next());
            }
            case 6 -> controller.printUserHabitsFilteredByStatus();
            case 7 -> controller.printAllUserHabitsFilteredByCreationDate();
            case 8 -> {
                print("Введите id привычки");
                id = scanner.nextInt();
                print("Введите кол-во дней период статистики от 1 до 30");
                print(controller.getHabitStatisticForLastNDays(id, scanner.nextInt()));
            }
            case 9 -> {
                print("Введите кол-во дней период статистики от 1 до 365");
                print(controller.getSuccessRateHabitStatisticForLastNDays(scanner.nextInt()));
            }
            case 10 -> {
                print("Введите id привычки для удаления");
                print(controller.deleteHabit(scanner.nextInt()));
            }
            case 11 -> controller.logout();
            case 0 -> print("Выход в меню");
            default -> print("Такой команды нет, введите команду снова.");
        }
    }

    private void doCommandsForAdminMenu() {
        controller.logout();
        print("Введите ваше имя");
        print(controller.createAdmin(scanner.next()));
        printMenuForAdmin();
        int command = scanner.nextInt();
        while (command != 6) {
            switch (command) {
                case 1 -> {
                    print("Введите email (login) пользователя для блокировки");
                    print(controller.blockUser(scanner.next()));
                }
                case 2 -> {
                    print("Введите email (login) пользователя для разблокировки");
                    print(controller.unblockUser(scanner.next()));
                }
                case 3 -> {
                    print("Введите email (login) пользователя для удаления из базы");
                    print(controller.deleteUser(scanner.next()));
                }
                case 4 -> controller.showAllUsers();
                case 5 -> {
                    print("Введите email (login) пользователя для получения списка его привычек");
                    controller.getAndShowAllUserHabits(scanner.next());
                }
                default -> print("Такой команды нет");
            }
            printMenuForAdmin();
            command = scanner.nextInt();
        }
        print(controller.logoutAdmin());
    }
}
