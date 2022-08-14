package quangnnfx16178.ilovevn.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import quangnnfx16178.ilovevn.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    @Query(value = "SELECT u FROM User u")
    Page<User> findAll(Pageable page);

    @Query(value = "SELECT count(u) FROM User u")
    Integer countAll();

    @Query("SELECT u FROM User u WHERE CONCAT(u.email, ' ', u.fullName, ' ', u.phoneNumber) LIKE %?1%")
    public Page<User> findAll(String keyword, Pageable pageable);
}
