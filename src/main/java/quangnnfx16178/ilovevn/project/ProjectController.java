package quangnnfx16178.ilovevn.project;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import quangnnfx16178.ilovevn.entity.Charity;
import quangnnfx16178.ilovevn.service.CharityService;
import quangnnfx16178.ilovevn.service.DonationService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin/project/")
@Log4j2
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CharityService charityService;

    @Autowired
    private DonationService donationService;

    @GetMapping("/viewProjects")
    public String viewProjects(Model model) {
        List<Project> projects = projectService.listAll();
        List<Charity> charities = charityService.listAll();
        List<Long> raisedAmounts = donationService.listAllProjectRaisedAmount();
        model.addAttribute("projects", projects);
        model.addAttribute("charities", charities);
        model.addAttribute("raisedAmounts", raisedAmounts);
        return "/admin/project";
    }

    @GetMapping("/create")
    public String createProjectForm(Model model) {
        List<Charity> charities = charityService.listAll();
        Project project = new Project();
        model.addAttribute("project", project);
        model.addAttribute("charities", charities);
        model.addAttribute("pageTitle", "Quản lý các dự án quyên góp từ thiện | Thêm dự án mới");
        model.addAttribute("numberOfExistingProjectImages", 0);
        return "/admin/project_form";
    }

    @GetMapping("/edit/{id}")
    public String editProject(@PathVariable("id") Integer id, Model model) {
        Project project = projectService.getProjectById(id);
        List<Charity> charities = charityService.listAll();
        Integer numberOfExistingProjectImages = project.getImages().size();

        model.addAttribute("project", project);
        model.addAttribute("charities", charities);
        model.addAttribute("pageTitle", "Sửa dự án số " + id);
        model.addAttribute("numberOfExistingProjectImages", numberOfExistingProjectImages);

        return "/admin/project_form";
    }

    @PostMapping({"/save", "edit/save"})
    public String saveProject(
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "charityId") Integer charityId,
            @RequestParam(name = "targetAmount") Long targetAmount,
            @RequestParam(name = "startedDate") Date startedDate,
            @RequestParam(name = "expiredDate") Date expiredDate,
            @RequestParam(name = "shortDescription") String shortDescription,
            @RequestParam(name = "fullDescription") String fullDescription,
            @RequestParam(value = "fileImage") MultipartFile mainImageMultipart,
            @RequestParam(value = "projectImage") MultipartFile[] projectImageMultiparts,
            @RequestParam(name = "imageIDs", required = false) String[] imageIDs,
            @RequestParam(name = "imageNames", required = false) String[] imageNames,
            HttpServletRequest request
            )
    {
        Project project = new Project();
        project.setImages(new HashSet<>());
        if (id != null) {
            project = projectService.getProjectById(id);
//            project.getImages().clear();
        }

        ProjectSaveHelper.setName(project, name);
        ProjectSaveHelper.setCharity(project, charityService.getCharityById(charityId));
        ProjectSaveHelper.setAmount(project, targetAmount);
        ProjectSaveHelper.setStartedDate(project, startedDate);
        ProjectSaveHelper.setExpiredDate(project, expiredDate);
        ProjectSaveHelper.setShortDescription(project, shortDescription);
        ProjectSaveHelper.setFullDescription(project, fullDescription);

        ProjectSaveHelper.setMainImageName(project, mainImageMultipart);
        ProjectSaveHelper.removeDeletedProjectImages(project, imageIDs, imageNames);
        ProjectSaveHelper.addNewProjectImage(project, projectImageMultiparts);

        Project persistedProject = projectService.save(project);

        String realPath = request.getSession().getServletContext().getRealPath("/");

        ProjectSaveHelper.saveUploadedImages(persistedProject, realPath, mainImageMultipart, projectImageMultiparts);
        ProjectSaveHelper.deleteProjectImagesWereRemovedOnForm(persistedProject, realPath);


        return "redirect:/admin/project/viewProjects";
    }
}