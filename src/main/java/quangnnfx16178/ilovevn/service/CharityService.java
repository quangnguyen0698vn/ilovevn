package quangnnfx16178.ilovevn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quangnnfx16178.ilovevn.entity.Charity;
import quangnnfx16178.ilovevn.repository.CharityRepository;

import java.util.List;

@Service
public class CharityService {
    @Autowired
    private CharityRepository repo;

    public List<Charity> listAll() {
        return repo.findAll();
    }

    public Charity getCharityById(Integer id) {
        return repo.findCharityById(id);
    }
}
