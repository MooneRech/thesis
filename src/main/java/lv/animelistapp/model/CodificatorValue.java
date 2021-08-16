package lv.animelistapp.model;

public class CodificatorValue {

    private long id;

    private String name;

    private String description;

    public CodificatorValue() {

    }

    public CodificatorValue(long id, String name) {
        this.id = id;
        this.name = name;
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
