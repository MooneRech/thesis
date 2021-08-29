package lv.animelistapp.repository;

import lv.animelistapp.mapper.AnimeListMapper;
import lv.animelistapp.model.AnimeListModel;
import lv.animelistapp.model.defaults.LvalUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("animeListRepository")
public class AnimeListRepository {

    @Autowired
    AnimeListMapper animeListMapper;

    private Long checkIfNull(long id) {
        return ((id == 0) ? null : id);
    }

    private Integer checkIfNull(int id) {
        return ((id == 0) ? null : id);
    }


    public List<AnimeListModel> getAnimeListForDialog() {
        LvalUserDetails user = (LvalUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return animeListMapper.getAnimeListForDialog(user.getUsername());
    }

    public List<AnimeListModel> getUserAnimeList(String status, String username) {
        Map map = new HashMap();
        map.put("user", username);
        map.put("status", status);

        List<AnimeListModel> result = animeListMapper.getUserAnimeList(map);
        return result;
    }

    public void addToList(AnimeListModel animeListModel) {
        LvalUserDetails user = (LvalUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        Map map = new HashMap();
        map.put("user_id", checkIfNull(user.getId()));
        map.put("anime_id", checkIfNull(animeListModel.getAnimeId()));
        map.put("progress", animeListModel.getUserEpisodes());
        map.put("score", animeListModel.getScore());
        map.put("status_id", checkIfNull(animeListModel.getUserStatusId()));

        animeListMapper.addToList(map);
        animeListModel.setId((long) map.get("p_id"));
    }

    public void updateListEntry(AnimeListModel animeListModel) {
        Map map = new HashMap();
        map.put("progress", animeListModel.getUserEpisodes());
        map.put("score", animeListModel.getScore());
        map.put("status_id", animeListModel.getUserStatusId());
        map.put("id", animeListModel.getId());

        animeListMapper.updateListEntry(map);
    }

    public void deleteAnimeTags(AnimeListModel value) {
        LvalUserDetails user = (LvalUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        Map map = new HashMap();
        map.put("anime_id", value.getAnimeId());
        map.put("user_id", user.getId());

        animeListMapper.deleteAnimeTags(map);
    }

    public void createAnimeTag(Long animeId, String tag) {
        LvalUserDetails user = (LvalUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        Map map = new HashMap();
        map.put("anime_id", animeId);
        map.put("user_id", user.getId());
        map.put("tag", tag);

        animeListMapper.createAnimeTag(map);
    }

    public  AnimeListModel getUserAnimeDetails(long animeId, long userId) {
        Map map = new HashMap();
        map.put("anime_id", animeId);
        map.put("user_id", userId);

        return animeListMapper.getUserAnimeDetails(map);
    }

}
