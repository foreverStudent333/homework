package habitsTrackApp.services;

import habitsTrackApp.model.Admin;
import habitsTrackApp.model.Habit;
import habitsTrackApp.model.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Интерфейс описывающий логику работы с классом {@link Admin}
 *
 * @author Mihail Harhan "mihaillKHn@yandex.ru"
 */
public interface AdminManager {
    /**
     * Создает в базе новую сущность {@code Admin}
     *
     * @param admin которого нужно создать
     */
    void createAdmin(Admin admin);

    /**
     * Удаляет сущность {@code Admin} из базы
     *
     * @param id по которому находит админа для удаления
     */
    void deleteAdmin(int id);

    /**
     * Блокирует пользователя с переданным емейл
     *
     * @param email пользователя, которого нужно заблокировать
     */
    void blockUser(String email);

    /**
     * Разблокирует пользователя с переданным емейл
     *
     * @param email пользователя, которого нужно разблокировать
     */
    void unblockUser(String email);

    /**
     * Удаляет юзера из базы
     *
     * @param email юзера которого нужно удалить
     */
    void deleteUser(String email);

    /**
     * Для получения всех пользователей
     *
     * @return мапу всех текущих пользователй по ключу email
     */
    HashMap<String, User> getAllUsers();

    /**
     * Для получения всех привычек пользователя
     *
     * @param email почта по которой найдем пользователя в базе
     * @return список всех привычек пользователя
     */
    ArrayList<Habit> getAllUserHabits(String email);
}
