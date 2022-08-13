package quangnnfx16178.ilovevn.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import quangnnfx16178.ilovevn.entity.Charity;
import quangnnfx16178.ilovevn.entity.Project;
import quangnnfx16178.ilovevn.util.ProjectSaveUtil;
import quangnnfx16178.ilovevn.service.ProjectService;
import quangnnfx16178.ilovevn.service.CharityService;
import quangnnfx16178.ilovevn.exception.ProjectNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;

/**
 * class ProjectController hiện tại quản lý tất cả các request đến /admin/projects/**
 * Sử dụng Log4j2 của Project Lomnbok để dễ dàng log ra console khi dự án được deploy để testing
 * @Author Nguyễn Ngọc Quang
 */
@Controller
@RequestMapping({"/admin/projects", "/admin/projects/"})
@Log4j2
@RequiredArgsConstructor
public class ProjectController {

    /**
     * Đường dẫn redirect mặc định về trang số 1, sắp xếp thứ tự dụ án theo trường id, thứ tự tăng dần
     * và hiển thị tất cả các dự án của các tổ chức đửng ra quyên góp từ thiện
     */
    private String defaultRedirectURL = "redirect:/admin/projects/viewPage/?pageNum=1&?sortField=id&sortDir=asc&charityId=0";

    /**
     * projectService được spring tự động khởi tạo và inject vào controller
     */

    private final ProjectService projectService;

    /**
     * charityService được spring tự động khởi tạo và inject vào controller
     */
    private final CharityService charityService;

    @GetMapping({"", "/"})
    public String listFirstPage(Model model) {
        return  defaultRedirectURL;
    }

    /**
     *
     * @param pageNum số trang mà người dùng muốn hiển thị
     * @param sortField tên trường mà người dùng muốn kết quả được sắp xếp theo
     * @param sortDir acs hoặc desc - sắp xếp theo thứ tự tăng dần hoặc giảm dần
     * @param charityId filter theo tổ chức đứng ra quyên góp
     * @param keyword từ khóa tìm kiếm - hệ thống sẽ tìm tất cả các dự án có  tên chứa từ khóa (không phân biết hoa thường)
     * @param model object chứa các attribute cho views - tham khảo thêm tại https://www.baeldung.com/spring-mvc-model-model-map-model-view
     * @return đường dẫn đến file view jsp
     */
    @GetMapping("/viewPage")
    public String listByPage(@RequestParam(name="pageNum", required = true, defaultValue = "1") Integer pageNum,
                             @RequestParam(name="sortField", required = true, defaultValue = "id") String sortField,
                             @RequestParam(name="sortDir", required = true, defaultValue = "asc") String sortDir,
                             @RequestParam(name = "charityId", required = true, defaultValue = "0") Integer charityId,
                             @RequestParam(name = "keyword", required = false) String keyword,
                             Model model
    ) {
        Page<Project> aPage = null;
        if (keyword == null || keyword.equals("")) {
            aPage = charityId == 0
                    ? projectService.listPage(pageNum, sortField, sortDir)
                    : projectService.listPageByCharityId(pageNum, sortField, sortDir, charityId);
        } else {
            aPage = charityId == 0
                    ? projectService.listPageWithNameContainsIgnoreCase(pageNum, sortField, sortDir, keyword)
                    : projectService.listPageByCharityWithNameContainsIgnoreCase(pageNum, sortField, sortDir, charityId, keyword);
        }
        List<Project> projects = aPage.getContent();
        List<Charity> charities = charityService.listAll();
        model.addAttribute("projects", projects);
        model.addAttribute("charities", charities);
        model.addAttribute("selectedCharityId", charityId);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);

        model.addAttribute("sortFieldNames", ProjectService.sortFieldNames);
        model.addAttribute("sortFieldValues", ProjectService.sortFieldValues);
        model.addAttribute("sortDirNames", ProjectService.sortDirNames);
        model.addAttribute("sortDirValues", ProjectService.sortDirValues);

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("currentKeyword", keyword);
        model.addAttribute("totalPages", aPage.getTotalPages());
        return "/admin/project";
    }


    /**
     * Chuyển tiếp đến form tạo mới dự án
     * @param model object chứa các attribute cho views - tham khảo thêm tại https://www.baeldung.com/spring-mvc-model-model-map-model-view
     * @return đường dẫn đến file view jsp
     */
    @GetMapping("/createForm")
    public String createProjectForm(Model model) {
        List<Charity> charities = charityService.listAll();
        Project project = new Project();
        model.addAttribute("project", project);
        model.addAttribute("charities", charities);
        model.addAttribute("pageTitle", "Quản lý các dự án quyên góp từ thiện | Thêm dự án mới");
        model.addAttribute("numberOfExistingProjectImages", 0);
        return "/admin/project_form";
    }

    /**
     * Chuyển tiếp đến form chỉnh sửa thông tin dự án
     * @param id mã số dự án
     * @param model object chứa các attribute cho views - tham khảo thêm tại https://www.baeldung.com/spring-mvc-model-model-map-model-view
     * @param ra lưu các attribute sử dụng trong view được redirect đến, đọc thêm tại https://www.baeldung.com/spring-redirect-and-forward
     * @return
     */

    @GetMapping("/edit/{id}")
    public String editProject(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        Project project = null;
        try {
            project = projectService.getProjectById(id);
            List<Charity> charities = charityService.listAll();
            Integer numberOfExistingProjectImages = project.getImages().size();

            model.addAttribute("project", project);
            model.addAttribute("charities", charities);
            model.addAttribute("pageTitle", "Sửa dự án số " + id);
            model.addAttribute("numberOfExistingProjectImages", numberOfExistingProjectImages);

            return "/admin/project_form";
        } catch (ProjectNotFoundException e) {
            ra.addFlashAttribute("success", false);
            ra.addFlashAttribute("message", e.getMessage());
            log.error(e.getMessage());
            return defaultRedirectURL;
        }
    }

    /**
     * Xóa MỘT dự án
     * @param id - mã số dự án
     * @param model object chứa các attribute cho views - tham khảo thêm tại https://www.baeldung.com/spring-mvc-model-model-map-model-view
     * @param ra lưu các attribute sử dụng trong view được redirect đến, đọc thêm tại https://www.baeldung.com/spring-redirect-and-forward
     * @param request object chứa các attribute của request
     * @return redirect đến trang quản lý dự án, thông báo nếu xóa thành công, xóa thất bại
     */
    @GetMapping("/delete/{id}")
    public String deleteSingleProject(@PathVariable("id") Integer id, Model model, RedirectAttributes ra, HttpServletRequest request) {
        try {
            projectService.deleteProjectById(id);
            ProjectSaveUtil.deleteProjecImagesFolder(id, request.getSession().getServletContext().getRealPath("/"));
            ra.addFlashAttribute("success", true);
            ra.addFlashAttribute("message", "Xóa thành công dự án số " + id + " khỏi hệ thống");
            log.info("Xóa thành công dự án số " + id + " khỏi hệ thống");
            return defaultRedirectURL;
        } catch (ProjectNotFoundException e) {
            ra.addFlashAttribute("success", false);
            ra.addFlashAttribute("message", e.getMessage());
            log.error(e.getMessage());
            return defaultRedirectURL;
        }
    }

    /**
     * Xóa Nhiều dự án
     * @param ids danh sách các mã số dự án cần xóa - spring sẽ tự động đọc các parameters và tạo ra list này
     * @param model object chứa các attribute cho views - tham khảo thêm tại https://www.baeldung.com/spring-mvc-model-model-map-model-view
     * @param ra lưu các attribute sử dụng trong view được redirect đến, đọc thêm tại https://www.baeldung.com/spring-redirect-and-forward
     * @param request object chứa các attribute của request
     * @return redirect đến trang quản lý dự án, thông báo nếu xóa thành công, xóa thất bại, ROLLBACK nếu có một dự án không xóa được, chỉ COMMIT khi tất cả đều được xóa
     */
    @GetMapping("/delete/")
    public String deleteMultipleProjects(@RequestParam(name = "id") List<Integer> ids, Model model, RedirectAttributes ra, HttpServletRequest request) {
        try {
            projectService.deleteProjectsByIds(ids);
            ProjectSaveUtil.deleteProjecImagesFolders(ids, request.getSession().getServletContext().getRealPath("/"));
            ra.addFlashAttribute("success", true);
            ra.addFlashAttribute("message", "Xóa thành công các dự án số" + ids + " khỏi hệ thống");
            log.info("Xóa thành công các dự án số" + ids + " khỏi hệ thống");
            return defaultRedirectURL;
        } catch (ProjectNotFoundException e) {
            ra.addFlashAttribute("success", false);
            ra.addFlashAttribute("message", e.getMessage());
            log.error(e.getMessage());
            return defaultRedirectURL;
        }
    }

    /**
     * Lưu dự án mới và lưu dự án được chỉnh sửa thông tin
     * @param id null nếu là dự án mới, ngược lại là dự án được chỉnh sửa
     * @param name tên dự án
     * @param charityId mã số của tổ chức đứng r quyên góp
     * @param targetAmount số tiền quyên góp dự kiến
     * @param startedDate ngày bắt đầu
     * @param expiredDate ngày kết thúc
     * @param shortDescription mô tả ngắn
     * @param fullDescription mô tả đầy đủ
     * @param mainImageMultipart Multipart của hình ảnh chính của dự án
     * @param projectImageMultiparts Mảng lưu Multipart của các hình ảnh mô tả dự án
     * @param imageNames Danh ách tên file hình ảnh mô tả dự án
     * @param request object chứa các attribute của request
     * @param ra lưu các attribute sử dụng trong view được redirect đến, đọc thêm tại https://www.baeldung.com/spring-redirect-and-forward
     * @return
     */

    @PostMapping({"/save", "edit/save"})
    public String saveProject(
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "charityId", required = false) Integer charityId,
            @RequestParam(name = "targetAmount", required = false) Long targetAmount,
            @RequestParam(name = "startedDate", required = false) Date startedDate,
            @RequestParam(name = "expiredDate", required = false) Date expiredDate,
            @RequestParam(name = "shortDescription") String shortDescription,
            @RequestParam(name = "fullDescription") String fullDescription,
            @RequestParam(value = "fileImage") MultipartFile mainImageMultipart,
            @RequestParam(value = "projectImage") MultipartFile[] projectImageMultiparts,
            @RequestParam(name = "imageNames", required = false) String[] imageNames,
            HttpServletRequest request,
            RedirectAttributes ra
            )
    {
        Project project = new Project();

        boolean flag = (id == null);

        try {
            if (!flag) project = projectService.getProjectById(id);
            ProjectSaveUtil.setName(project, name);
//            log.info("dự án đã bắt đầu chưa? " + project.isAlreadyStarted());
//            log.info(startedDate);
            if (flag || !project.isAlreadyStarted()) {
//                log.info("This block is stepped into");
                ProjectSaveUtil.setCharity(project, charityService.getCharityById(charityId));
                ProjectSaveUtil.setTargetAmount(project, targetAmount);
                ProjectSaveUtil.setStartedDate(project, startedDate);
//                log.info(project.getStartedDate());
                ProjectSaveUtil.setExpiredDate(project, expiredDate);
            }

            ProjectSaveUtil.setShortDescription(project, shortDescription);
            ProjectSaveUtil.setFullDescription(project, fullDescription);

            ProjectSaveUtil.setMainImageName(project, mainImageMultipart);
            ProjectSaveUtil.removeDeletedProjectImages(project, imageNames);
            ProjectSaveUtil.addNewProjectImage(project, projectImageMultiparts);

            Project persistedProject = projectService.save(project);

            String realPath = request.getSession().getServletContext().getRealPath("/");

            ProjectSaveUtil.saveUploadedImages(persistedProject, realPath, mainImageMultipart, projectImageMultiparts);
            ProjectSaveUtil.deleteProjectImagesWereRemovedOnForm(persistedProject, realPath);

            if(flag) {
                log.info("Tạo mới thành công dự án " + persistedProject.getId());
                ra.addFlashAttribute("success", true);
                ra.addFlashAttribute("message", "Tạo mới thành công dự án " + persistedProject.getId());
            } else {
                log.info("Chỉnh sửa thành công dự án " + persistedProject.getId());
                ra.addFlashAttribute("success", true);
                ra.addFlashAttribute("message", "Chỉnh sửa thành công dự án " + persistedProject.getId());
            }
            return defaultRedirectURL;
        } catch (ProjectNotFoundException e) {
            ra.addFlashAttribute("success", false);
            ra.addFlashAttribute("message", e.getMessage());
            log.warn(e.getMessage());
            return defaultRedirectURL;
        }
    }

//    Below is some of my testing in concurrency
//    @GetMapping("/test")
//    @Transactional
//    public String testResult() {
//        log.info("this method is called");
//        Project project = projectService.getProjectByIdWithLocking(1);
//        log.info("Load project wih locking successfully " + project.getName());
//        try {
//            TimeUnit.SECONDS.sleep(60);
//        } catch (InterruptedException ie) {
//            Thread.currentThread().interrupt();
//        }
//        log.info("release the lock");
//        return "/index";
//    }
}