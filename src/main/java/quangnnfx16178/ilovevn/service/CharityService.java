package quangnnfx16178.ilovevn.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import quangnnfx16178.ilovevn.entity.Charity;
import quangnnfx16178.ilovevn.repository.CharityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharityService {
    private final CharityRepository repo;

    public final static Integer DEFAULT_ITEMS_PER_PAGE = 3;

    public List<Charity> listAll() {
        return repo.findAll();
    }
    public List<Charity> listAll(Integer pageNum) {
        Pageable page = PageRequest.of(pageNum-1, DEFAULT_ITEMS_PER_PAGE, Sort.by("id").ascending());
        Page aPage = repo.findAll(page);
        return aPage.getContent();
    }

    public Charity getCharityById(Integer id) {
        return repo.findCharityById(id);
    }

    public Integer countAll() {
        return repo.countAll();
    }
}
