package lv.animelistapp.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserMapper {

     void registerUser(Map map);

}
