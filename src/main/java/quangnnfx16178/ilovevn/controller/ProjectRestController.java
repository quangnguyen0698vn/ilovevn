package quangnnfx16178.ilovevn.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import quangnnfx16178.ilovevn.entity.Project;
import quangnnfx16178.ilovevn.exception.ProjectNotFoundException;
import quangnnfx16178.ilovevn.service.ProjectService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/rest/"})
@Log4j2
@RequiredArgsConstructor
public class ProjectRestController {
    private final ProjectService projectService;

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    Iterable<Project> getProjects() {

        Iterable<Project> projects = projectService.listAll();
//        for (Project project : projects)
//            log.info(project.getId() + " " + project.getName());
        return projects;
    }

    @GetMapping("/{id}")
    Project getProjectById(@PathVariable String id) {
        try {
            return projectService.getProjectById(Integer.parseInt(id));
        } catch (ProjectNotFoundException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
