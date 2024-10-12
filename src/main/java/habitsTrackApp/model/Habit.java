package habitsTrackApp.model;

public class Habit {
    private Integer id;
    private String name;
    private String description;
    private HabitsType habitsType;

    public Habit(String name, String description, HabitsType habitsType) {
        this.name = name;
        this.description = description;
        this.habitsType = habitsType;
    }

    public Habit(String name, String description) {
        this.name = name;
        this.description = description;
        this.habitsType = HabitsType.CUSTOM;
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
}
