package interview.userhandlerbackend.repository;

import interview.userhandlerbackend.model.User;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  boolean existsByUsername(String username);

  User findByUsername(String username);

}
