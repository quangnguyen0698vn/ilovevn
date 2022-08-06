package quangnnfx16178.ilovevn.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quangnnfx16178.ilovevn.project.Project;
import quangnnfx16178.ilovevn.project.ProjectRepository;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository repo;

    public List<Project> listAll() {
        return repo.findAll();
    }

    public Project getProjectById(Integer id) {
        return repo.findProjectById(id);
    }

    public Project save(Project project) {
        return repo.save(project);
    }
}
