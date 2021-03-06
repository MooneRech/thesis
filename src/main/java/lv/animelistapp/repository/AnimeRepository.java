package lv.animelistapp.repository;

import lv.animelistapp.mapper.AnimeMapper;
import lv.animelistapp.model.AnimeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("animeRepository")
public class AnimeRepository {

    @Autowired
    AnimeMapper animeMapper;

    private Long checkIfNull(long id) {
        return ((id == 0) ? null : id);
    }

    private Integer checkIfNull(int id) {
        return ((id == 0) ? null : id);
    }

    public AnimeDetails getAnimeById(long id) {
        return animeMapper.getAnimeById(id);
    }

    public void createAnimeEntity(AnimeDetails animeDetails){
        Map map = new HashMap();
        map.put("p_title_jp", animeDetails.getTitleJP());
        map.put("p_title_en", animeDetails.getTitleEN());
        map.put("p_title_lv", animeDetails.getTitleLV());
        map.put("p_type_id", checkIfNull(animeDetails.getTypeId()));
        map.put("p_episodes", checkIfNull(animeDetails.getEpisodes()));
        map.put("p_status_id", checkIfNull(animeDetails.getStatusId()));
        map.put("p_aired_from", animeDetails.getAiredFrom());
        map.put("p_aired_to", animeDetails.getAiredTo());
        map.put("p_studios_id", checkIfNull(animeDetails.getStudiosId()));
        map.put("p_source_id", checkIfNull(animeDetails.getSourceId()));
        map.put("p_duration", checkIfNull(animeDetails.getDuration()));
        map.put("p_rating_id", checkIfNull(animeDetails.getRatingId()));
        map.put("p_image", animeDetails.getImage());
        map.put("p_image_name", animeDetails.getImageName());
        map.put("p_description", animeDetails.getDescription());

        animeMapper.createAnimeEntity(map);
        animeDetails.setId((Long) map.get("p_out_id"));
        animeDetails.setImageId((Long) map.get("p_out_image_id"));
    }

    public void updateAnimeEntity(AnimeDetails animeDetails) {
        Map map = new HashMap();
        map.put("p_title_jp", animeDetails.getTitleJP());
        map.put("p_title_en", animeDetails.getTitleEN());
        map.put("p_title_lv", animeDetails.getTitleLV());
        map.put("p_type_id", checkIfNull(animeDetails.getTypeId()));
        map.put("p_episodes", checkIfNull(animeDetails.getEpisodes()));
        map.put("p_status_id", checkIfNull(animeDetails.getStatusId()));
        map.put("p_aired_from", animeDetails.getAiredFrom());
        map.put("p_aired_to", animeDetails.getAiredTo());
        map.put("p_studios_id", checkIfNull(animeDetails.getStudiosId()));
        map.put("p_source_id", checkIfNull(animeDetails.getSourceId()));
        map.put("p_duration", checkIfNull(animeDetails.getDuration()));
        map.put("p_rating_id", checkIfNull(animeDetails.getRatingId()));
        map.put("p_image", animeDetails.getImage());
        map.put("p_image_name", animeDetails.getImageName());
        map.put("p_id", animeDetails.getId());
        map.put("p_image_id", checkIfNull(animeDetails.getImageId()));
        map.put("p_description", animeDetails.getDescription());

        animeMapper.updateAnimeEntity(map);
    }

    public void deleteAnimeEntity(long animeId) {
        animeMapper.deleteAnimeEntity(animeId);
    }

    public List<AnimeDetails> getAnimeList() {
        return animeMapper.getAnimeList();
    }

    public AnimeDetails getAnimePageById(long animeId) {
        return animeMapper.getAnimePageById(animeId);
    }

    public List<AnimeDetails> getArrivals() {
        return animeMapper.getArrivals();
    }

    public List<AnimeDetails> getAnimeListForMainPage(String title, long typeId,
                                                      long ratingId, long studiosId,
                                                      String genre) {
        Map map = new HashMap();
        map.put("title", (title == null) ? null : "%"+title+"%");
        map.put("type_id", checkIfNull(typeId));
        map.put("rating_id", checkIfNull(ratingId));
        map.put("studios_id", checkIfNull(studiosId));
        map.put("genre", (genre == null) ? null : "%"+genre+"%");

        return animeMapper.getAnimeListForMainPage(map);
    }

}
