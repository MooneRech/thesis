package lv.animelistapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import lv.animelistapp.mapper.UserMapper;
import lv.animelistapp.model.User;

import java.util.HashMap;
import java.util.Map;

@Repository("userRepository")
public class UserRepository {

    @Autowired
    UserMapper userMapper;


    public void registerUser(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPasswordEncrypted(bCryptPasswordEncoder.encode(user.getPassword()));

        Map map = new HashMap();
        map.put("username", user.getUsername());
        map.put("password", user.getPasswordEncrypted());
        map.put("email", user.getEmail());

        userMapper.registerUser(map);
    }

}
