package lv.animelistapp.mapper;

import lv.animelistapp.model.User;
import lv.animelistapp.model.UserRoles;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

     void registerUser(Map map);

     User getUserDetails(String username);

     List<UserRoles> getUserRolesById(long userId);

}
