package quangnnfx16178.ilovevn.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import quangnnfx16178.ilovevn.exception.ProjectNotFoundException;
import quangnnfx16178.ilovevn.project.Project;
import quangnnfx16178.ilovevn.project.ProjectRepository;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Service của entity Project
 */
@Service
public class ProjectService {

    /**
     * Số lượng project được liệt kê ở một trang
     */
    public final static Integer ITEMS_PER_PAGE = 4;

    /**
     * Các trường có thể được sort ở menu sort and filter
     */
    public static List<String> sortFieldNames = new ArrayList<>(
            Arrays.asList("Mã số",
                    "Tên dự án",
                    "Ngày bắt đầu",
                    "Ngày kết thúc",
                    "Số tiền quyên góp được",
                    "Số tiền dự kiến quyên góp",
                    "Tiến độ")
    );

    /**
     * Các trường có thể được sort ở menu sort and filter
     */
    public static List<String> sortFieldValues = new ArrayList<>(
            Arrays.asList("id",
                    "name",
                    "startedDate",
                    "expiredDate",
                    "raisedAmount",
                    "targetAmount",
                    "raisedPercentage")
    );

    /**
     * Thứ tự sort tăng dần, giảm dần
     */
    public static List<String> sortDirNames = new ArrayList<>(
            Arrays.asList("Tăng dần",
                    "Giảm dần")
    );

    public static List<String> sortDirValues = new ArrayList<>(
            Arrays.asList("asc",
                    "desc")
    );

    /**
     * repository được spring tự đọc khởi tạo và inject vào
     */
    @Autowired
    private ProjectRepository repo;

    /**
     * Load toàn bộ dự án ra khỏi database
     * @return
     */
    public List<Project> listAll() {
        return repo.findAll();
    }

    /**
     * Load dự án id ra khỏi database
     * @param id mã số dự án
     * @return dự án cần load
     * @throws ProjectNotFoundException exception cho việc không tìm thấy dự án
     */
    public Project getProjectById(Integer id) throws ProjectNotFoundException {
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new ProjectNotFoundException("Dự án với mã số " + id + " không tồn tại trong cơ sở dữ liệu");
        }
    }

    /**
     * Persist project vào database
     * @param project
     * @return pesisted project object
     */
    public Project save(Project project) {
        return repo.save(project);
    }

    /**
     * Load các project của trang pageNum
     * @param pageNum trang cần load
     * @param sortField trường cần sort
     * @param sortDir thứ tự sort
     * @return danh sách dự án Page<Project> thay vì List<Project>, phục vụ cho việc phân trang
     * đọc thêm tại https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html
     */
    public Page<Project> listPage(Integer pageNum, String sortField, String sortDir) {
        Pageable page = sortDir.equals("asc")
                ? PageRequest.of(pageNum-1, ITEMS_PER_PAGE, Sort.by(sortField).ascending())
                : PageRequest.of(pageNum-1, ITEMS_PER_PAGE, Sort.by(sortField).descending());
        return repo.findAll(page);
    }

    /**
     * Load các project của trang pageNum, filter theo tổ chức quyên góp
     * @param pageNum trang cần load
     * @param sortField trường cần sort
     * @param sortDir thứ tự sort
     * @param charityId mã số của tổ chức quyên góp
     * @return danh sách dự án Page<Project> thay vì List<Project>, phục vụ cho việc phân trang
     * đọc thêm tại https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html
     */
    public Page<Project> listPageByCharityId(Integer pageNum, String sortField, String sortDir, Integer charityId) {
        Pageable page = sortDir.equals("asc")
                ? PageRequest.of(pageNum-1, ITEMS_PER_PAGE, Sort.by(sortField).ascending())
                : PageRequest.of(pageNum-1, ITEMS_PER_PAGE, Sort.by(sortField).descending());
        return repo.findAllByCharityId(page, charityId);
    }

    /**
     * Load các project của trang pageNum, search theo từ khóa keyword
     * @param pageNum trang cần load
     * @param sortField trường cần sort
     * @param sortDir thứ tự sort
     * @param keyword từ khóa tìm kiếm
     * @return danh sách dự án Page<Project> thay vì List<Project>, phục vụ cho việc phân trang
     * đọc thêm tại https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html
     */
    public Page<Project> listPageWithNameContainsIgnoreCase(Integer pageNum, String sortField, String sortDir, String keyword) {
        Pageable page = sortDir.equals("asc")
                ? PageRequest.of(pageNum-1, ITEMS_PER_PAGE, Sort.by(sortField).ascending())
                : PageRequest.of(pageNum-1, ITEMS_PER_PAGE, Sort.by(sortField).descending());
        return repo.findAllWithNameContainsIgnoreCase(page, "%"+keyword+"%");
    }

    /**
     * Load các project của trang pageNum, filter theo tổ chức quyên góp, search theo từ khóa keyword
     * @param pageNum trang cần load
     * @param sortField trường cần sort
     * @param sortDir thứ tự sort
     * @param charityId mã số của tổ chức quyên góp
     * @param keyword từ khóa tìm kiếm
     * @return danh sách dự án Page<Project> thay vì List<Project>, phục vụ cho việc phân trang
     * đọc thêm tại https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html
     */
    public Page<Project> listPageByCharityWithNameContainsIgnoreCase(Integer pageNum, String sortField, String sortDir, Integer charityId, String keyword) {
        Pageable page = sortDir.equals("asc")
                ? PageRequest.of(pageNum-1, ITEMS_PER_PAGE, Sort.by(sortField).ascending())
                : PageRequest.of(pageNum-1, ITEMS_PER_PAGE, Sort.by(sortField).descending());
        return repo.findAllByCharityIdWithNameContainsIgnoreCase(page, charityId, "%"+keyword+"%");
    }

    /**
     * Method này chưa được sử dụng, phục vụ cho việc load một dự án khỏi database và lock nó lại, tránh các case của concurrency
     * @param id
     * @return dự án được load
     */
    public Project getProjectByIdWithLocking(Integer id) {
        return repo.findByProjectIdWithLocking(id);
    }

    /**
     * Xóa dự án có mã số id khỏi database
     * @param id mã số của dự án
     * @throws ProjectNotFoundException nếu không tìm thấy dự án thì báo lỗi
     */
    @Transactional(rollbackOn = {ProjectNotFoundException.class})
    public void deleteProjectById(Integer id) throws ProjectNotFoundException {
        Integer count = repo.countById(id);
        if (count == null || count == 0) {
            throw new ProjectNotFoundException("Dự án với mã số " + id + " không tồn tại trong cơ sở dữ liệu");
        }
        repo.deleteById(id);
    }


    /**
     * Xóa CÁC dự án có mã số id khỏi database
     * Sẽ ROLLBACK toàn bộ nếu CHỈ một dự án không xóa được
     * @param ids các mã số của dự án
     * @throws ProjectNotFoundException nếu không tìm thấy dự án thì báo lỗi
     */
    @Transactional(rollbackOn = {ProjectNotFoundException.class})
    public void deleteProjectsByIds(List<Integer> ids) throws ProjectNotFoundException {
        for(Integer id : ids) {
            deleteProjectById(id);
        }
    }
}
