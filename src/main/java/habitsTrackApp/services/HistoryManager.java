package habitsTrackApp.services;

import habitsTrackApp.model.Habit;
import habitsTrackApp.model.HabitStatus;
import habitsTrackApp.model.User;

import java.time.LocalDateTime;
import java.util.SortedMap;

/**
 * Интерфейс описывающий логику работы сохранения истории всех привычек
 *
 * @author Mihail Harhan "mihaillKHn@yandex.ru"
 */
public interface HistoryManager {
    /**
     * Создает историю прогресса выполнения привычки
     *
     * @param habit привычка которую надо занести в историю
     */
    void createHabitHistory(Habit habit);

    /**
     * Обновляет историю прогресса выполнения привычки со статусом {@code HabitType.DAILY}
     *
     * @param habit привычка у которой нужно обновить историю прогресса выполнения
     */
    void updateDailyHabitHistory(Habit habit);

    /**
     * Обновляет историю прогресса выполнения привычки со статусом {@code HabitType.WEEKLY}
     *
     * @param habit привычка у которой нужно обновить историю прогресса выполнения
     */
    void updateWeeklyHabitHistory(Habit habit);

    /**
     * Удаляет историю прогресса выполнения привычки
     *
     * @param habit историю которой надо удалить
     */
    void deleteHabitFromHistory(Habit habit);

    /**
     * Удаляет историю прогресса выполнения всех привычек пользователя
     *
     * @param user историю привычек которого надо удалить
     */
    void deleteAllUserHabitsFromHistory(User user);

    /**
     * Фильтрует историю выполнения привычки за последние {@code days}
     *
     * @param habit историю которой фильтрует метод
     * @param days  за прошедшее кол-во дней будет выдана история
     * @return возвращает отфильтрованную мапу истории выполнения {@code habit} за последние {@code days}
     */
    SortedMap<LocalDateTime, HabitStatus> getHabitStatisticsForGivenPeriod(Habit habit, Integer days);

    /**
     * Метод находит процент завершенных привычек среди всех привычек за последние {@code days}
     * и возвращает этот процент.
     *
     * @param user у которого нужно найти процент завершенных привычек
     * @param days за какое кол-во прошедших дней нужно выдавать статистику
     * @return {@code Integer} число - процент завершенных привычек среди
     */
    Integer getSuccessPercentOfUsersFinishedHabitsForGivenPeriod(User user, Integer days);
}
