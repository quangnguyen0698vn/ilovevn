package quangnnfx16178.ilovevn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import quangnnfx16178.ilovevn.entity.Donation;

import javax.persistence.ColumnResult;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Integer> {
    @Query(value = "SELECT d FROM Donation d")
    public List<Donation> findAll();

    @Query(value = "SELECT sum(d.amount) from donations d group by d.project_id order by d.project_id", nativeQuery = true)
    public List<Long> findAllProjectRaisedAmount();
}
