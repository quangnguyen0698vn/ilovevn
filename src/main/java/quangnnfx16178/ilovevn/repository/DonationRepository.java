package quangnnfx16178.ilovevn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import quangnnfx16178.ilovevn.entity.Donation;
import quangnnfx16178.ilovevn.entity.StateType;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Integer> {
    @Query(value = "SELECT d FROM Donation d")
    public List<Donation> findAll();

    @Query(value = "SELECT sum(d.amount) from donations d group by d.project_id order by d.project_id", nativeQuery = true)
    public List<Long> findAllProjectRaisedAmount();


    @Query(value = "select project_id from " +
            "( select distinct d.project_id, sum(d.amount) over (partition by d.project_id) as raise_amount from donations d) tmp_result_set " +
            "order by raise_amount asc;", nativeQuery = true
    )
    public List<Integer> findProjectIdSortByRaisedAmountAsc();
    Integer countAllByState(StateType state);

    @Query("SELECT sum(d.amount) FROM Donation d WHERE d.state = :state")
    Long sumAllByState(StateType state);

    @Query("SELECT count(d) FROM Donation d")
    Integer countAll();

    @Query("SELECT d FROM Donation d WHERE CONCAT(d.fullName, ' ', d.transRefNo) LIKE %?1%")
    Page<Donation> findAll(String keyword, Pageable pageable);

    Integer countById(Integer id);
}
