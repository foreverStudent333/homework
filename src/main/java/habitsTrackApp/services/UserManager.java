package habitsTrackApp.services;

import habitsTrackApp.model.User;

/**
 * Интерфейс описывающий логику работы с классом {@link User}
 *
 * @author Mihail Harhan "mihaillKHn@yandex.ru"
 */
public interface UserManager {
    /**
     * Добавляет нового юзера в базу, проверяет валидность email и выдает юзеру уникальный id
     *
     * @param user новый, которого нужно занести в базу
     */
    void addNewUser(User user);

    /**
     * Находит и возвращает {@code User} по переданному email
     *
     * @param email по которому ищем юзера
     * @return юзера с почтой {@code email}
     */
    User getUserByEmail(String email);

    /**
     * Авторизовывает юзера в системе, проверяет корректность его email и пароля
     *
     * @param email    по которому юзер находится в базе
     * @param password пароль который нужно проверить принадлежит ли {@code user} и корректный ли
     * @return {@code User} из базы юзеров
     */
    User authorizeUser(String email, String password);

    /**
     * Удаляет юзера из базы и всю его историю выполнения привычек
     *
     * @param user которого нужно удалить
     */
    void deleteUser(User user);

    /**
     * Изменяет пароль пользователя проверяя верный ли передан старый пароль
     * @param user у которого меняется пароль
     * @param oldPassword старый пароль для проверки
     * @param newPassword новый пароль
     */
    void changePassword(User user, String oldPassword, String newPassword);

    /**
     * Изменяет имя пользователя
     * @param user у которого меняется имя
     * @param newName новое имя
     */
    void changeName(User user, String newName);

    /**
     * Изменяет email у юзера, проверяя его на валидность
     * @param user у которого нужно поменять email
     * @param newEmail новый email
     */
    void changeEmail(User user, String newEmail);

    /**
     * Метод вызывается при смене дня. Сбрасывает статус прогресса выполнения ежедневных привычек юзера
     * с {@code HabitStatus.IN_PROGRESS} на {@code HabitStatus.IN_PROGRESS}
     *
     * @param user у которого нужно обновить статус привычек
     */
    void resetAllDoneDailyHabits(User user);

    /**
     * Метод вызывается при смене недели. Сбрасывает статус прогресса выполнения ежедневных
     * и еженедельных привычек юзера с {@code HabitStatus.IN_PROGRESS} на {@code HabitStatus.IN_PROGRESS}
     *
     * @param user у которого нужно обновить статус привычек
     */
    void resetAllDoneWeeklyHabits(User user);

    void changePasswordByEmailRecoveryCode(User user, String newPassword);
}
