package habitsTrackApp.services;

import habitsTrackApp.model.Habit;
import habitsTrackApp.model.HabitStatus;
import habitsTrackApp.model.User;

import java.util.ArrayList;

/**
 * Интерфейс описывающий логику работы с классом {@link Habit}
 *
 * @author Mihail Harhan "mihaillKHn@yandex.ru"
 */
public interface HabitsManager {
    /**
     * Добавляет новую привычку в базу
     *
     * @param user  к которому будет привязана новая привычка
     * @param habit новая привычка
     */
    void addNewHabit(User user, Habit habit);

    /**
     * удаляет привычку юзера
     *
     * @param user у которого надо удалить привычку
     * @param id   привычки которую надо удалить
     */
    void deleteHabitById(User user, Integer id);

    /**
     * Обновляет имя привычки
     *
     * @param user    у которого надо обновить привычку
     * @param habit   привычка которую нужно обновит
     * @param newName новое имя на которое нужно обновить
     */
    void updateHabitName(User user, Habit habit, String newName);

    /**
     * Обновляет описание привычки
     *
     * @param user           у которого надо обновить привычку
     * @param habit          привычка которую нужно обновит
     * @param newDescription новое описание на которое нужно обновить
     */
    void updateHabitDescription(User user, Habit habit, String newDescription);

    /**
     * Обновляет статус привычки
     *
     * @param user      у которого надо обновить привычку
     * @param habit     привычка которую нужно обновит
     * @param newStatus новый статус который нужно установить
     */
    void updateHabitStatus(User user, Habit habit, HabitStatus newStatus);

    /**
     * Устанавливает статус всех привычек на {@code HabitStatus.FINISHED}
     *
     * @param user у которого надо поменять статус всех привычек
     */
    void setEveryHabitStatusFinished(User user);

    /**
     * @param user у которого берем привычку
     * @param habitId уникальный id привычки
     * @return {@code Habit} по уникальному переданному id
     */
    Habit getHabitById(User user, int habitId);

    /**
     * Returns {@code null} если нет такого юзера в базе.
     * Если юзер есть вытаскиваю из мапы все привычки юзера
     * и делаю из них ArrayList
     *
     * @param user это пользователь по которому выдадим все его привычки
     * @return возвращаем список всех привычек юзера
     */
    ArrayList<Habit> getAllUserHabits(User user);

    /**
     * Returns {@code null} если нет такого юзера в базе.
     *
     * @param user это пользователь привычки которого фильтруем.
     *             {@code status} это статус по которому фильтруем
     * @return возвращаем список только тех привычек которые
     * имеют статус переданный в параметре {@code status}
     */
    ArrayList<Habit> getUserHabitsWithACertainStatus(User user, HabitStatus status);

    /**
     * Метод для сортировки всех привычек юзера по {@code HabitStatus}
     * в естественном порядке (будет сортировка по возрастанию NEW, IN_PROGRESS, FINISHED)
     * и вернуть отсортированный список привычек
     *
     * @param user у которого будет сортировка привычек
     * @return возвращает отсортированный список привычек
     */
    ArrayList<Habit> getUserHabitsFilteredByStatus(User user);

    /**
     * Метод для сортировки всех {@code Habit} у {@code User} по дате создания привычки
     * и вернуть отсортированный список привычек
     *
     * @param user у которого будет сортировка привычек
     * @return возвращает отсортированный список привычек
     */
    ArrayList<Habit> getAllUserHabitsFilteredByCreationDate(User user);
}
