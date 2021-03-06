package lv.animelistapp.repository;

import lv.animelistapp.mapper.CodificatorMapper;
import lv.animelistapp.model.Codificator;
import lv.animelistapp.model.CodificatorValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("codificatorRepository")
public class CodificatorRepository {

    @Autowired
    CodificatorMapper codificatorMapper;

    public List<Codificator> getCodifList() {
        return codificatorMapper.getCodifList();
    }

    public List<CodificatorValue> getCodifValuesByCode(String code) {
        return codificatorMapper.getCodifValuesByCode(code);
    }

    public void deleteCodifValueById(long id) {
        codificatorMapper.deleteCodifValueById(id);
    }

    public void updateCodifValue(CodificatorValue value) {
        Map map = new HashMap();
        map.put("id", value.getId());
        map.put("name", value.getName());
        map.put("description", value.getDescription());
        codificatorMapper.updateCodifValue(map);
    }

    public void createCodifValue(CodificatorValue value, String code) {
        Map map = new HashMap();
        map.put("name", value.getName());
        map.put("description", value.getDescription());
        map.put("code", code);
        codificatorMapper.createCodifValue(map);
    }

    public void addGenreToAnime(long animeId, long genreId) {
        Map map = new HashMap();
        map.put("anime_id", animeId);
        map.put("genre_id", genreId);
        codificatorMapper.addGenreToAnime(map);
    }

    public List<CodificatorValue> getGenreListByAnimeId(long id) {
        return codificatorMapper.getGenreListByAnimeId(id);
    }

    public void deleteGenresByAnimeId(long animeId) {
        codificatorMapper.deleteGenresByAnimeId(animeId);
    }

    public CodificatorValue getSpecificCdvValue(String value, String code) {
        Map map = new HashMap();
        map.put("value", value);
        map.put("code", code);
        return codificatorMapper.getSpecificCdvValue(map);
    }

}
