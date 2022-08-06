package quangnnfx16178.ilovevn.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quangnnfx16178.ilovevn.entity.User;
import quangnnfx16178.ilovevn.repository.UserRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository repo;

  public List<User> listAll() {
    return repo.findAll();
  }
}
