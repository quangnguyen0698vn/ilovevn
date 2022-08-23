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
@RequestMapping({"/api/"})
@Log4j2
@RequiredArgsConstructor
public class ProjectRestController {
    private final ProjectService projectService;

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    Iterable<Project> getProjects() {

        Iterable<Project> projects = projectService.listAllStartedProjects();
//        for (Project project : projects)
//            log.info(project.getId() + " " + project.getName());
        return projects;
    }

    @GetMapping(value = "/projects/page/{pageNum}")
    Iterable<Project> listPage(@PathVariable("pageNum") Integer pageNum) {
        Iterable<Project> projects = projectService.listPageStartedProjects(pageNum);
        return projects;
    }

    @GetMapping(value = "projects/size")
    Integer numberOfProjects() {
        return projectService.countAllStartedProjects();
    }

}
