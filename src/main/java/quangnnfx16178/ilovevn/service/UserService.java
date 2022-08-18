package quangnnfx16178.ilovevn.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import quangnnfx16178.ilovevn.entity.AuthenticationType;
import quangnnfx16178.ilovevn.entity.User;
import quangnnfx16178.ilovevn.exception.ProjectNotFoundException;
import quangnnfx16178.ilovevn.exception.UserNotFoundException;
import quangnnfx16178.ilovevn.repository.UserRepository;

import javax.transaction.Transactional;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {
  private final UserRepository repo;
  private final PasswordEncoder passwordEncoder;

  private final EmailServiceImpl emailService;

  public final static Integer DEFAULT_ITEMS_PER_PAGE = 5;
  public Iterable<User> listAll() {
    return repo.findAll();
  }

  public Integer countAll() { return repo.countAll(); }

  public Page<User> listByPage(int pageNum, int pageSize, String sortField, String sortDir, String keyword) {
    Sort sort = Sort.by(sortField);

    sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

    Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);

    if (keyword != null && !keyword.equals("")) {
      return repo.findAll(keyword, pageable);
    }

    return repo.findAll(pageable);
  }

  @Transactional(rollbackOn = {UserNotFoundException.class, DataIntegrityViolationException.class})
  public void updateUserEnabledStatus(Integer id, boolean enabled) {
    repo.updateEnabledStatus(id, enabled);
  }

  @Transactional(rollbackOn = {UserNotFoundException.class, DataIntegrityViolationException.class})
  public void deleteUserById(Integer id) throws UserNotFoundException, DataIntegrityViolationException {
    Integer count = repo.countUserById(id);
    if (count == null || count == 0) {
      throw new UserNotFoundException("Người dùng với mã số " + id + " không tồn tại trong cơ sở dữ liệu");
    }
    User user = repo.findById(id).get();
    if (user.getRole().getName().equals("ADMIN")) {
      throw new IllegalArgumentException("không thể xóa tài khoản ADMIN");
    }
    repo.deleteById(id);
  }

  public boolean isEmailUnique(String email) {
    log.info("checking unique email: " + email);
    Integer count = repo.countUserByEmail(email);
    log.info(count);
    return count == 0;
  }

  public void registerUser(String fullName, String email, String password, String address, String phoneNumber) {

//    String encodePassword = password;
    String encodePassword = passwordEncoder.encode(password);

    User aUser = new User();
    aUser.setFullName(fullName);
    aUser.setEmail(email);
    aUser.setPassword(encodePassword);
    aUser.setAddress(address);
    aUser.setPhoneNumber(phoneNumber);
    aUser.setAuthenticationType(AuthenticationType.DATABASE);

    User persistedUser = repo.save(aUser);
    sendDefaultPasswordInfo(persistedUser, password);
  }

  private void sendDefaultPasswordInfo(User user, String password) {
    emailService.sendDefaultPasswordToNewUser(user, password);
  }

  public void updateAuthenticationType(User user, AuthenticationType type) {
    if (!user.getAuthenticationType().equals(type)) {
      repo.updateAuthenticationType(user.getId(), type);
    }
  }
}
