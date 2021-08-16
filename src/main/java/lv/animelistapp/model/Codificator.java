package lv.animelistapp.model;

import java.util.List;

public class Codificator {

    public final static String GENRE = "GENRE";
    public final static String RATING = "RATING";
    public final static String TYPE = "TYPE";
    public final static String STATUS = "STATUS";
    public final static String STUDIOS = "STUDIOS";
    public final static String SOURCE = "SOURCE";

    private long id;

    private String code;

    private String name;

    private String description;

    private List<CodificatorValue> values;

    public List<CodificatorValue> getValues() {
        return values;
    }

    public void setValues(List<CodificatorValue> values) {
        this.values = values;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
