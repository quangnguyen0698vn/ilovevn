package quangnnfx16178.ilovevn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import quangnnfx16178.ilovevn.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    @Query(value = "SELECT u FROM User u")
    Page<User> findAll(Pageable page);

    @Query(value = "SELECT count(u) FROM User u")
    Integer countAll();

    @Query("SELECT u FROM User u WHERE CONCAT(u.email, ' ', u.fullName, ' ', u.phoneNumber) LIKE %?1%")
    Page<User> findAll(String keyword, Pageable pageable);

    @Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    Integer countUserById(Integer id);
}
