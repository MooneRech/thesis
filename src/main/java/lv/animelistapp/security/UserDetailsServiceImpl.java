package lv.animelistapp.security;

import lv.animelistapp.model.defaults.LvalUserDetails;
import lv.animelistapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LvalUserDetails lvalUserDetails = new LvalUserDetails(userRepository.getUserDetails(username));
        lvalUserDetails.setRoles(userRepository.getUserRolesById(lvalUserDetails.getId()));
        return lvalUserDetails;
    }

}
