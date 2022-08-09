package quangnnfx16178.ilovevn.donation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import quangnnfx16178.ilovevn.donation.Donation;

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


}