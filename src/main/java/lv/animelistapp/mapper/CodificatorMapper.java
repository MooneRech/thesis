package lv.animelistapp.mapper;

import lv.animelistapp.model.Codificator;
import lv.animelistapp.model.CodificatorValue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CodificatorMapper {

    List<Codificator> getCodifList();

    List<CodificatorValue> getCodifValuesByCode(String code);

    void deleteCodifValueById(long id);

    void updateCodifValue(Map map);

    void createCodifValue(Map map);

}
