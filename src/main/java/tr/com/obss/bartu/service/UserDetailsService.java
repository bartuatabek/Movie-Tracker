package tr.com.obss.bartu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.com.obss.bartu.dao.UserRepository;
import tr.com.obss.bartu.model.User;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User existingUser = repository.findUsersByUsernameContainingIgnoreCase(username);
            List<GrantedAuthority> authorities = new ArrayList<>();
            if (existingUser.getRole().getName().equals("Admin")) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            } else {
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(existingUser.getUsername(),
                    existingUser.getPassword(), authorities);
            return userDetails;
        } catch (EntityNotFoundException e) {
            return null;
        }
    }
}
