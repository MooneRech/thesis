package lv.animelistapp.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AnimeDetails {

    private long id;

    private String titleJP;

    private String titleEN;

    private String titleLV;

    private long imageId;

    private byte[] image;

    private String imageName;

    private long typeId;

    private String type;

    private long ratingId;

    private String rating;

    private long studiosId;

    private String studios;

    private long statusId;

    private String status;

    private LocalDate airedFrom;

    private LocalDate airedTo;

    private int episodes;

    private int duration;

    private long sourceId;

    private String source;

    private String description;

    private String genresString;

    private double avgScore;

    public String getAvrScoreString() {
        return String.format("%,.2f", avgScore);
    }

    public double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(double avgScore) {
        this.avgScore = avgScore;
    }

    private List<CodificatorValue> genres;

    public String getGenresString() {
        return genresString;
    }

    public void setGenresString(String genresString) {
        this.genresString = genresString;
    }

    public double getDurDouble() {
        return (double) duration;
    }

    public void setDurDouble(double durDouble) {
        this.duration = (int) durDouble;
    }

    public double getEpDouble() {
        return (double) episodes;
    }

    public void setEpDouble(double epDouble) {
        this.episodes = (int) epDouble;
    }

    public List<CodificatorValue> getGenres() {
        return genres;
    }

    public void setGenres(List<CodificatorValue> genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public String getAiredPeriod() {
        String text = "";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        if(airedFrom == null) {
            text = text + "-";
        } else {
            text = text + airedFrom.format(dateFormat);
        }

        text = text + " - ";

        if(airedTo == null) {
            text = text + "-";
        } else {
            text = text + airedTo.format(dateFormat);
        }

        return text;
    }

    public LocalDate getAiredTo() {
        return airedTo;
    }

    public void setAiredTo(LocalDate airedTo) {
        this.airedTo = airedTo;
    }

    public LocalDate getAiredFrom() {
        return airedFrom;
    }

    public void setAiredFrom(LocalDate airedFrom) {
        this.airedFrom = airedFrom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public String getStudios() {
        return studios;
    }

    public void setStudios(String studios) {
        this.studios = studios;
    }

    public long getStudiosId() {
        return studiosId;
    }

    public void setStudiosId(long studiosId) {
        this.studiosId = studiosId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public long getRatingId() {
        return ratingId;
    }

    public void setRatingId(long ratingId) {
        this.ratingId = ratingId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public String getTitleENLV() {
        String text = "";
        if(titleEN != null && !titleEN.equals("")) {
            text = text + titleEN;
        }

        if(!text.equals("") && titleLV != null && !titleLV.equals("")) {
            text = text + " / ";
        }

        if(titleLV != null && !titleLV.equals("")) {
            text = text + titleLV;
        }

        return text;
    }

    public String getTitleLV() {
        return titleLV;
    }

    public void setTitleLV(String titleLV) {
        this.titleLV = titleLV;
    }

    public String getTitleEN() {
        return titleEN;
    }

    public void setTitleEN(String titleEN) {
        this.titleEN = titleEN;
    }

    public String getTitleJP() {
        return titleJP;
    }

    public void setTitleJP(String titleJP) {
        this.titleJP = titleJP;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
