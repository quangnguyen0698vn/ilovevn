package quangnnfx16178.ilovevn.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Integer> {
    @Query("SELECT p FROM Project p")
    public List<Project> findAll();

    @Query("SELECT p FROM Project p WHERE p.id=:id")
    public Project findProjectById(@Param("id") Integer id);
}
