package quangnnfx16178.ilovevn.charity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import quangnnfx16178.ilovevn.charity.Charity;

import java.util.List;

public interface CharityRepository extends JpaRepository<Charity, Integer> {
    @Query("SELECT c FROM Charity c ORDER BY c.id")
    public List<Charity> findAll();

    @Query("SELECT c FROM Charity c WHERE c.id=:id")
    public Charity findCharityById(@Param("id") Integer id);
}
