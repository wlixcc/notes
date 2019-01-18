package wl.userservice.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import wl.userservice.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
