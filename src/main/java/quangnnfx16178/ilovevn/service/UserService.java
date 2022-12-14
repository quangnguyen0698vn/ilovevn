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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import quangnnfx16178.ilovevn.entity.AuthenticationType;
import quangnnfx16178.ilovevn.entity.User;
import quangnnfx16178.ilovevn.exception.UserNotFoundException;
import quangnnfx16178.ilovevn.repository.RoleRepository;
import quangnnfx16178.ilovevn.repository.UserRepository;
import quangnnfx16178.ilovevn.util.FileUploadUtil;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {
  private final UserRepository userRepository;

  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  private final EmailServiceImpl emailService;

  public final static Integer DEFAULT_ITEMS_PER_PAGE = 5;
  public Iterable<User> listAll() {
    return userRepository.findAll();
  }

  public Integer countAll() { return userRepository.countAll(); }

  public Page<User> listByPage(int pageNum, int pageSize, String sortField, String sortDir, String keyword) {
    Sort sort = Sort.by(sortField);

    sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

    Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);

    if (keyword != null && !keyword.equals("")) {
      return userRepository.findAll(keyword, pageable);
    }

    return userRepository.findAll(pageable);
  }

  @Transactional(rollbackOn = {UserNotFoundException.class, DataIntegrityViolationException.class})
  public void updateUserEnabledStatus(Integer id, boolean enabled) {
    userRepository.updateEnabledStatus(id, enabled);
  }

  @Transactional(rollbackOn = {UserNotFoundException.class, DataIntegrityViolationException.class})
  public void deleteUserById(Integer id) throws UserNotFoundException, DataIntegrityViolationException {
    Integer count = userRepository.countUserById(id);
    if (count == null || count == 0) {
      throw new UserNotFoundException("Ng?????i d??ng v???i m?? s??? " + id + " kh??ng t???n t???i trong c?? s??? d??? li???u");
    }
    User user = userRepository.findById(id).get();
    if (user.getRole().getName().equals("ADMIN")) {
      throw new IllegalArgumentException("kh??ng th??? x??a t??i kho???n ADMIN");
    }
    userRepository.deleteById(id);
  }

  public boolean isEmailUnique(String email) {
    log.info("checking unique email: " + email);
    Integer count = userRepository.countUserByEmail(email);
    log.info(count);
    return count == 0;
  }

  public User registerUser(User user, MultipartFile avatar, HttpServletRequest request) {
    String password = user.getPassword();
    user.setPassword(passwordEncoder.encode(password));
    User persistedUser = userRepository.save(user);
    sendDefaultPasswordInfo(persistedUser, password);

    if (avatar != null && !avatar.isEmpty()) {
      String fileName = StringUtils.cleanPath(avatar.getOriginalFilename());
      String realPath = request.getSession().getServletContext().getRealPath("/");
      String uploadDir = realPath + "WEB-INF/images/" + "user-images/" + persistedUser.getId();
      FileUploadUtil.cleanDir(uploadDir);
      FileUploadUtil.saveFile(uploadDir, fileName, avatar);
    }


    return persistedUser;
  }

  public void registerUser(String fullName, String email, String password, String address, String phoneNumber, MultipartFile avatar, HttpServletRequest request) {
    User aUser = new User();
    aUser.setFullName(fullName);
    aUser.setEmail(email);
    aUser.setPassword(password);
    aUser.setAddress(address);
    aUser.setPhoneNumber(phoneNumber);
    aUser.setAuthenticationType(AuthenticationType.DATABASE);
    aUser.setRole(roleRepository.findById(2).get());
    aUser.setEnabled(true);

    if (avatar != null && !avatar.isEmpty()) {
      String fileName = StringUtils.cleanPath(avatar.getOriginalFilename());
      aUser.setProfilePhoto(fileName);
    }

    registerUser(aUser, avatar, request);
  }

  private void sendDefaultPasswordInfo(User user, String password) {
    emailService.sendDefaultPasswordToNewUser(user, password);
  }

  public void updateAuthenticationType(User user, AuthenticationType type) {
    if (!user.getAuthenticationType().equals(type)) {
      userRepository.updateAuthenticationType(user.getId(), type);
    }
  }

    public User getByEmail(String email) throws UserNotFoundException {
      Integer count = userRepository.countUserByEmail(email);
      if (count == null || count == 0) {
        throw new UserNotFoundException("Ng?????i d??ng v???i email " + email + " kh??ng t???n t???i trong c?? s??? d??? li???u");
      }
      return userRepository.findByEmail(email);
    }

    public User getById(Integer id) throws UserNotFoundException {
      Integer count = userRepository.countUserById(id);
      if (count == null || count == 0) {
        throw new UserNotFoundException("Ng?????i d??ng v???i m?? s??? " + id + " kh??ng t???n t???i trong c?? s??? d??? li???u");
      }
      return userRepository.findById(id).get();
    }

  public void save(User user) {
     userRepository.save(user);
  }

  public void saveEditedUser(User user, MultipartFile avatar, HttpServletRequest request) {
    if (avatar != null && !avatar.isEmpty()) {
      String fileName = StringUtils.cleanPath(avatar.getOriginalFilename());
      user.setProfilePhoto(fileName);
    }
    save(user);

    if (avatar != null && !avatar.isEmpty()) {
      String fileName = StringUtils.cleanPath(avatar.getOriginalFilename());
      user.setProfilePhoto(fileName);
      String realPath = request.getSession().getServletContext().getRealPath("/");
      String uploadDir = realPath + "WEB-INF/images/" + "user-images/" + user.getId();
      FileUploadUtil.cleanDir(uploadDir);
      FileUploadUtil.saveFile(uploadDir, fileName, avatar);
    }
  }

  public boolean checkCorrectedPassword(User user, String password) {
    return passwordEncoder.matches(password, user.getPassword());
  }

  public void saveChangedPassword(User user, String password) {
    user.setPassword(passwordEncoder.encode(password));
    userRepository.save(user);
  }

    public void updateResetToken(String email, String token, String sessionId) throws UserNotFoundException {
      User user = getByEmail(email);
      user.setResetPasswordToken(token);
      user.setResetPasswordSessionId(sessionId);
      user.setResetPasswordRequestTime(new Date());
      user.setResetPasswordAttempt(0);
      userRepository.save(user);
      emailService.sendResetTokenEmail(user, token);
    }

  public User getByResetToken(String token) throws UserNotFoundException {
    Integer count = userRepository.countUserByResetPasswordToken(token);
    if (count == null || count == 0) {
      throw new UserNotFoundException("Kh??ng t??m th???y user v???i token: " + token);
    }
    return userRepository.findByResetPasswordToken(token);
  }

  public void saveResetPassword(User user, String password) {
    user.setPassword(passwordEncoder.encode(password));
    user.setResetPasswordToken("");
    user.setResetPasswordSessionId("");
    userRepository.save(user);
  }

    public User getBySessionId(String sessionId) throws UserNotFoundException {
      Integer count = userRepository.countUserByresetPasswordSessionId(sessionId);
      if (count == null || count == 0) {
        throw new UserNotFoundException("Kh??ng t??m th???y user c?? y??u c???u t??m l???i m???t kh???u v???i sessionId: " + sessionId);
      }
      return userRepository.findByResetPasswordSessionId(sessionId);
    }

  public void clearForgotPasswordRequest(User user) {
    user.setResetPasswordToken(null);
    user.setResetPasswordSessionId(null);
    user.setResetPasswordRequestTime(null);
    user.setResetPasswordAttempt(0);
    userRepository.save(user);
  }

  public void saveUser(User user) {
    userRepository.save(user);
  }
}
