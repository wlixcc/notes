package wl.userservice.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wl.userservice.Util.PasswordEncoderUtil;
import wl.userservice.user.dao.UserRepository;
import wl.userservice.user.entity.Role;
import wl.userservice.user.entity.User;

import java.util.Arrays;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
       return userRepository.save(user);
    }


}
