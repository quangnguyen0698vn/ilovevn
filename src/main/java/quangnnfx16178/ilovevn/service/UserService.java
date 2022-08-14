package quangnnfx16178.ilovevn.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import quangnnfx16178.ilovevn.entity.User;
import quangnnfx16178.ilovevn.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository repo;
  public final static Integer DEFAULT_ITEMS_PER_PAGE = 5;
  public Iterable<User> listAll() {
    return repo.findAll();
  }

  public Integer countAll() { return repo.countAll(); }

  public Page<User> listByPage(int pageNum, int pageSize, String sortField, String sortDir, String keyword) {
    Sort sort = Sort.by(sortField);

    sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

    Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);

    if (keyword != null) {
      return repo.findAll(keyword, pageable);
    }

    return repo.findAll(pageable);
  }
}
