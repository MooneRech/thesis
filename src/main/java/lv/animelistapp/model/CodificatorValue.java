package lv.animelistapp.model;

public class CodificatorValue {

    public static final String WATCHING = "Skatāms";
    public static final String COMPLETED = "Pabeigts";
    public static final String ON_HOLD = "Pieturēts";
    public static final String DROPPED = "Nomests";
    public static final String PWT = "Plāno skatīties";

    private long id;

    private String name;

    private String description;

    public CodificatorValue() {

    }

    public CodificatorValue(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getNameAndDescription() {
        return (name == null || name.isEmpty()) ? "" : "(" + name + ") " + description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
