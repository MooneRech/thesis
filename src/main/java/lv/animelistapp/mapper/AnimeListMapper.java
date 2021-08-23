package lv.animelistapp.mapper;

import lv.animelistapp.model.AnimeListModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AnimeListMapper {

    List<AnimeListModel> getAnimeListForDialog(String username);

    List<AnimeListModel> getUserAnimeList(Map map);

    void addToList(Map map);

    void updateListEntry(Map map);

    void deleteAnimeTags(Map map);

    void createAnimeTag(Map map);

}
