package habitsTrackApp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Habit {
    private Integer id;
    private String name;
    private String description;
    private HabitsType habitsType;
    private HabitsStatus habitsStatus;
    private LocalDateTime startDate;

    public Habit(String name, String description, HabitsType habitsType) {
        this.name = name;
        this.description = description;
        this.habitsType = habitsType;
        this.habitsStatus = HabitsStatus.NEW;
        this.startDate = LocalDateTime.now();
    }

    public Habit(String name, String description) {
        this.name = name;
        this.description = description;
        this.habitsType = HabitsType.CUSTOM;
        this.habitsStatus = HabitsStatus.NEW;
        this.startDate = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HabitsType getHabitsType() {
        return habitsType;
    }

    public void setHabitsType(HabitsType habitsType) {
        this.habitsType = habitsType;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public HabitsStatus getHabitsStatus() {
        return habitsStatus;
    }

    public void setHabitsStatus(HabitsStatus habitsStatus) {
        this.habitsStatus = habitsStatus;
    }
}
