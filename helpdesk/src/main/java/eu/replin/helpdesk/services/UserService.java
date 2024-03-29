package eu.replin.helpdesk.services;

import eu.replin.helpdesk.utils.Role;
import eu.replin.helpdesk.utils.RoleRepository;
import eu.replin.helpdesk.domain.User;
import eu.replin.helpdesk.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service("userService")
public class UserService {


    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return findUserByEmail(authentication.getName());
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    public boolean checkPassword(String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =  findUserByEmail(authentication.getName());
        if (user.getPassword().equals(bCryptPasswordEncoder.encode(password))) {
            return true;
        } else {
            return false;
        }
    }

    public void changePassword(String newpwd) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =  findUserByEmail(authentication.getName());
        user.setPassword(bCryptPasswordEncoder.encode(newpwd));
    }

}
