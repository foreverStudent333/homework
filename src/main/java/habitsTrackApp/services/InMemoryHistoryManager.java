package habitsTrackApp.services;

import habitsTrackApp.model.Habit;
import habitsTrackApp.model.HabitStatus;
import habitsTrackApp.model.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class InMemoryHistoryManager implements HistoryManager {
    private TreeMap<LocalDateTime, HabitStatus> habitProgressHistory;
    private final HashMap<Habit, TreeMap<LocalDateTime, HabitStatus>> habitsProgressHistoryMap;
    private HashMap<Habit, TreeMap<LocalDateTime, HabitStatus>> habitsProgressHistoryMapByUserId;

    public InMemoryHistoryManager() {
        habitProgressHistory = new TreeMap<>();
        habitsProgressHistoryMap = new HashMap<>();
        habitsProgressHistoryMapByUserId = new HashMap<>();
    }

    @Override
    public void createHabitHistory(Habit habit) {
        TreeMap<LocalDateTime, HabitStatus> habitProgressHistory = new TreeMap<>();
        habitProgressHistory.put(habit.getStartDate(), habit.getHabitStatus());
        habitsProgressHistoryMap.put(habit, habitProgressHistory);
    }

    @Override
    public void updateDailyHabitHistory(Habit habit) {
        habitProgressHistory = habitsProgressHistoryMap.get(habit);
        if (habitProgressHistory.size() == 1) {
            habitProgressHistory.put(habit.getStartDate().plusSeconds(1), habit.getHabitStatus());
            habitsProgressHistoryMap.put(habit, habitProgressHistory);
        } else {
            habitProgressHistory.put(habitProgressHistory.lastKey().plusDays(1), habit.getHabitStatus());
            habitsProgressHistoryMap.put(habit, habitProgressHistory);
        }
    }

    @Override
    public void updateWeeklyHabitHistory(Habit habit) {
        habitProgressHistory = habitsProgressHistoryMap.get(habit);
        if (habitProgressHistory.size() == 1) {
            habitProgressHistory.put(habit.getStartDate().plusSeconds(1), habit.getHabitStatus());
            habitsProgressHistoryMap.put(habit, habitProgressHistory);
        } else {
            habitProgressHistory.put(habitProgressHistory.lastKey().plusDays(7), habit.getHabitStatus());
            habitsProgressHistoryMap.put(habit, habitProgressHistory);
        }
    }

    @Override
    public void deleteHabitFromHistory(Habit habit) {
        habitsProgressHistoryMap.remove(habit);
    }

    @Override
    public void deleteAllUserHabitsFromHistory(User user) {
        habitsProgressHistoryMap.entrySet().removeIf(
                entry -> (user.getId().equals(entry.getKey().getUserOwnerId()))
        );
    }

    @Override
    public SortedMap<LocalDateTime, HabitStatus> getHabitStatisticsForGivenPeriod(Habit habit, Integer days) {
        habitProgressHistory = habitsProgressHistoryMap.get(habit);
        LocalDateTime startFromDay = habitProgressHistory.lastKey().minusDays(days);
        return habitProgressHistory.tailMap(startFromDay);
    }

    @Override
    public Integer getSuccessPercentOfUsersFinishedHabitsForGivenPeriod(User user, Integer days) {
        int resultPercent = 0;
        LocalDateTime startFromDay;
        habitsProgressHistoryMapByUserId = getHabitsProgressHistoryMapByUserId(user.getId());
        for (Habit habit : habitsProgressHistoryMapByUserId.keySet()) {
            habitProgressHistory = habitsProgressHistoryMapByUserId.get(habit);
            startFromDay = habitProgressHistory.lastKey().minusDays(days);
            resultPercent += getSuccessPercentOfFinishedHabit(habitProgressHistory.tailMap(startFromDay));
        }
        return resultPercent;
    }

    public HashMap<Habit, TreeMap<LocalDateTime, HabitStatus>> getHabitsProgressHistoryMapByUserId(Integer id) {
        HashMap<Habit, TreeMap<LocalDateTime, HabitStatus>> habitsProgressHistoryMapByUserId = new HashMap<>();
        habitsProgressHistoryMap.forEach((habit, treeMap) -> {
            if (habit.getUserOwnerId().equals(id)) {
                habitsProgressHistoryMapByUserId.put(habit, treeMap);
            }
        });
        return habitsProgressHistoryMapByUserId;
    }

    private Integer getSuccessPercentOfFinishedHabit(SortedMap<LocalDateTime, HabitStatus> habits) {
        int percentOfFinishedHabits = 0;
        for (LocalDateTime time : habits.keySet()) {
            if (habits.get(time).equals(HabitStatus.FINISHED)) {
                percentOfFinishedHabits += 100;
            }
        }
        return percentOfFinishedHabits / habits.size();
    }
}
