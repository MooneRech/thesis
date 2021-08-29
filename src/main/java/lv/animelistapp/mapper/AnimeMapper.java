package lv.animelistapp.mapper;

import lv.animelistapp.model.AnimeDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AnimeMapper {

    AnimeDetails getAnimeById(long id);

    void createAnimeEntity(Map map);

    void updateAnimeEntity(Map map);

    void deleteAnimeEntity(long animeId);

    List<AnimeDetails> getAnimeList();

    AnimeDetails getAnimePageById(long animeId);

}
