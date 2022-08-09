package quangnnfx16178.ilovevn.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import quangnnfx16178.ilovevn.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {

  @Query("SELECT u FROM User u")
  public List<User> findAll();
}
