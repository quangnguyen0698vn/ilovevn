package quangnnfx16178.ilovevn.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import quangnnfx16178.ilovevn.entity.Project;

import java.util.List;

// https://www.baeldung.com/spring-data-jpa-pagination-sorting

/**
 * Repository làm viêc với bảng projects của database
 * Sử dụng JPQL dể truy vấn dữ liệu
 * Nếu gặp vấn đề khó của thể sử dụng native SQL
 *
 * @Author Nguyễn Ngọc Quang
 */
public interface ProjectRepository extends PagingAndSortingRepository<Project, Integer> {
    /**
     * Load hết dự án ra khỏi database, method này đã không được sử dụng nữa
     * @return danh sách dự án List<Project>
     */
//    @Query(value = "SELECT p FROM Project p")
//    List<Project> findAll();

//    @Query(value = "SELECT p FROM Project p WHERE p.id=:id")
//    Project findProjectById(@Param("id") Integer id);

//    @Query(value = "SELECT sum(d.amount) FROM Project p INNER JOIN p.donations d GROUP BY p.id HAVING p.id=:id")
//    Long findRaisedAmountByProjectId(Integer id);

    /**
     * Load một phần dự án ra khỏi database (sử dụng phân trang)
     * @param page chưa điều kiện phân trang, săp xếp, tìm kiếm
     * @return danh sách dự án Page<Project> thay vì List<Project>, phục vụ cho việc phân trang
     * đọc thêm tại https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html
     */
    @Query(value = "SELECT p FROM Project p")
    Page<Project> findAll(Pageable page);
    @Query(value = "SELECT count(p) FROM Project p")
    Integer countAll();

    @Query(value = "SELECT p FROM Project p WHERE p.charity.id=:charityId")
    Page<Project> findAllByCharityId(Pageable page, Integer charityId);

    @Query(value = "SELECT COUNT(p) FROM Project p WHERE p.charity.id=:charityId")
    Integer countAllByCharityId(Integer charityId);

    @Query(value = "SELECT p FROM Project p WHERE lower(p.name) like lower(:keyword)")
    Page<Project> findAllWithNameContainsIgnoreCase(Pageable pageable, String keyword);

    @Query(value = "SELECT count(p) FROM Project p WHERE lower(p.name) like lower(:keyword)")
    Integer countAllWithNameContainsIgnoreCase(String keyword);

    @Query(value = "SELECT p FROM Project p WHERE p.charity.id=:charityId AND lower(p.name) like lower(:keyword)")
    Page<Project> findAllByCharityIdWithNameContainsIgnoreCase(Pageable page, Integer charityId, String keyword);

    @Query(value = "SELECT count(p) FROM Project p WHERE p.charity.id=:charityId AND lower(p.name) like lower(:keyword)")
    Integer countAllByCharityIdWithNameContainsIgnoreCase(Integer charityId, String keyword);

    Project findByProjectIdWithLocking(Integer id);

    Integer countById(Integer id);

}
