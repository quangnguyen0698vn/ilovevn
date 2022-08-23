package quangnnfx16178.ilovevn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quangnnfx16178.ilovevn.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role getById(Integer id);
}
