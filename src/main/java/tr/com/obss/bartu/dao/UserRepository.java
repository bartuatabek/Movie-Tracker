package tr.com.obss.bartu.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.obss.bartu.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUsersByUsernameContainingIgnoreCase(String username);

    User findByUsername(String username);

    List<User> findUsersByEmail(String email);
}
