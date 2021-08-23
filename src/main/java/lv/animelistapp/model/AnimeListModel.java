package lv.animelistapp.model;

import java.util.List;

public class AnimeListModel {

    private long id;

    private long animeId;

    private String titleJP;

    private String titleEN;

    private String titleLV;

    private String type;

    private String rating;

    private long userStatusId;

    private String userStatus;

    private int episodes;

    private int userEpisodes;

    private int score;

    private String genresString;

    private String tagsString;

    private List<String> genres;

    private List<String> tags;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getTagsString() {
        return (tagsString == null) ? "" : tagsString;
    }

    public void setTagsString(String tagsString) {
        this.tagsString = tagsString;
    }

    public String getGenresString() {
        return (genresString == null) ? "" : genresString;
    }

    public void setGenresString(String genresString) {
        this.genresString = genresString;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getProgress() {
        return userEpisodes + "/" + ((episodes == 0) ? "-" : episodes);
    }

    public int getUserEpisodes() {
        return userEpisodes;
    }

    public void setUserEpisodes(int userEpisodes) {
        this.userEpisodes = userEpisodes;
    }

    public int getEpisodes() {
        return episodes;
    }

    public String getEpAsString() {
        return (episodes == 0) ? "-" : String.valueOf(episodes);
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public long getUserStatusId() {
        return userStatusId;
    }

    public void setUserStatusId(long userStatusId) {
        this.userStatusId = userStatusId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public long getAnimeId() {
        return animeId;
    }

    public void setAnimeId(long animeId) {
        this.animeId = animeId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
