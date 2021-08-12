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
}
