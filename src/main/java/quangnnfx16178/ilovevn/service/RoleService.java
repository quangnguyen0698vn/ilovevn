package quangnnfx16178.ilovevn.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quangnnfx16178.ilovevn.entity.Role;
import quangnnfx16178.ilovevn.repository.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repo;

    public List<Role> listAll() {
        return  repo.findAll();
    }

    public Role getById(Integer roleId) {
        return  repo.getById(roleId);
    }
}
