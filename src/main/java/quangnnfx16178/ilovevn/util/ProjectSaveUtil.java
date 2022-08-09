package quangnnfx16178.ilovevn.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import quangnnfx16178.ilovevn.entity.Charity;
import quangnnfx16178.ilovevn.entity.Project;
import quangnnfx16178.ilovevn.entity.ProjectImage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Log4j2
public class ProjectSaveUtil {

    /**
     * Set tên dự án
     * @param project object của dự án 
     * @param name tên dự án
     */
    public static void setName(Project project, String name) {
        project.setName(name);
    }

    /**
     * Set tổ chức đứng ra quyên góp
     * @param project object của dự án 
     * @param charity object của tổ chức đứng ra quyên góp
     */

    public static void setCharity(Project project, Charity charity) {
        project.setCharity(charity);
    }

    /**
     * Set số tiền dự kiến quyên góp
     * @param project object của dự án
     * @param targetAmount số tiền dự kiến quyên góp
     */
    public static void setTargetAmount(Project project, Long targetAmount) {
        project.setTargetAmount(targetAmount);
    }

    /**
     * Set ngày bắt đầu quyên góp
     * @param project object của dự án
     * @param startedDate ngày bắt đầu quyên góp
     */
    public static void setStartedDate(Project project, Date startedDate) {
        project.setStartedDate(startedDate);
    }

    /**
     * Set ngày kêt thúc quyên góp
     * @param project object của dự án
     * @param expiredDate ngày kêt thúc quyên góp
     */
    public static void setExpiredDate(Project project, Date expiredDate) {
        project.setExpiredDate(expiredDate);
    }


    /**
     * Set mô tả ngắn của dự án
     * @param project object của dự án
     * @param shortDescription mô tả ngắn của dự án
     */
    public static void setShortDescription(Project project, String shortDescription) {
        project.setShortDescription(shortDescription);
    }

    /**
     * Set mô tả đầy đủ của dự án
     * @param project object của dự án
     * @param fullDescription mô tả chi tiết của dự án
     */
    public static void setFullDescription(Project project, String fullDescription) {
        project.setFullDescription(fullDescription);
    }

    /**
     * Set tên tệp tin lưu trữ hình ảnh chinh của dự án
     * @param project object của dự án
     * @param mainImageMultipart multipart của hình ảnh chính
     */

    public static void setMainImageName(Project project, MultipartFile mainImageMultipart) {
        if (mainImageMultipart != null && !mainImageMultipart.isEmpty()) {
            String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
            project.setMainImage(fileName);
        }
    }

    /**
     * Người dùng có thể thêm hình ảnh mới, xóa hình ảnh cũ
     * Tiến hành duyệt qua danh sách các hình ảnh của dự án, nếu tên file đã bị xóa thì xóa khỏi danh sách
     * Thao tác này cần được tiến hành thật cẩn thận vì liên qua đến persist thông tin hình ảnh vào database
     * @param project object của dự án
     * @param imageNames danh sách tên hình ảnh hiện tại
     */
    public static void removeDeletedProjectImages(Project project, String[] imageNames) {
        if (imageNames == null || imageNames.length == 0) return;
        Set<String> existingFileNames = new HashSet<>();
        for (int i = 0; i < imageNames.length; i++) {
            // Integer id = Integer.parseInt(imageIDs[i]);
            String fileName = imageNames[i];
            existingFileNames.add(fileName);
        }

        Iterator<ProjectImage> it = project.getImages().iterator();
        while (it.hasNext()) {
            ProjectImage image = it.next();
            if (existingFileNames.contains(image.getFileName())) continue;
            it.remove();
        }
    }

    /**
     * Người dùng có thể thêm hình ảnh mới, xóa hình ảnh cũ
     * Tiến hành duyệt qua các hình ảnh mới được post lên và cập nhật vào danh sách tên hình ảnh của dự án
     * Thao tác này cần được tiến hành thật cẩn thận vì liên qua đến persist thông tin hình ảnh vào database
     * @param project object của dự án
     * @param projectImageMultiparts danh sách multiparts của những hình ảnh mới được gửi lên server từ html form
     */
    public static void addNewProjectImage(Project project, MultipartFile[] projectImageMultiparts) {
        if (projectImageMultiparts == null || projectImageMultiparts.length == 0) return;
        for (MultipartFile image : projectImageMultiparts) {
            if (image.isEmpty()) continue;
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            project.getImages().add(new ProjectImage(project, fileName));
        }
    }


    /**
     * method này dùng để lưu trữ hình ảnh từ html form -> file trên ổ đĩa ở server
     * @param project object của dự án - đã được persist để đảm bảo id != null
     * @param realPath đường dẫn đến thư mục cha của thư mục "WEB-INF"
     * @param mainImageMultipart multipart của hình ảnh chính
     * @param projectImageMultiparts multiparts của các hình ảnh minh họa
     */
    public static void saveUploadedImages(Project project, String realPath, MultipartFile mainImageMultipart, MultipartFile[] projectImageMultiparts) {
        if (mainImageMultipart != null && !mainImageMultipart.isEmpty()) {
            String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
            String uploadDir = realPath + "WEB-INF/images/" + "project-images/" + project.getId() + "/main";
            // There's only one main image
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, mainImageMultipart);
        }

        if (projectImageMultiparts != null && projectImageMultiparts.length > 0) {
            for (MultipartFile image : projectImageMultiparts) {
                if (image == null || image.isEmpty()) continue;
                String fileName = StringUtils.cleanPath(image.getOriginalFilename());
                String uploadDir = realPath + "WEB-INF/images/" + "project-images/" + project.getId();
                FileUploadUtil.saveFile(uploadDir, fileName, image);
            }
        }
    }

    /**
     * method này dùng để XÓA khỏi ổ đĩa những hình ảnh mà người dùng đã xóa khỏi html form
     * @param project object của dự án - đã được persist để đảm bảo id != null
     * @param realPath đường dẫn đến thư mục cha của thư mục "WEB-INF"
     */
    public static void deleteProjectImagesWereRemovedOnForm(Project project, String realPath) {
        String uploadDir = realPath + "WEB-INF/images/" + "project-images/" + project.getId();
        Path dirPath = Paths.get(uploadDir);
        try {
            Files.list(dirPath).forEach(file -> {
                File aFile = file.toFile();
                String fileName = aFile.getName();
                if (aFile.isFile() && !project.containsImageName(fileName)) {
                    try {
                        Files.delete(file);
                        log.info("Deleted project image: " + fileName);
                    } catch (IOException e) {
                        log.error("Could not delete project image: " + fileName);
                    }
                }
            });
        } catch (IOException e) {
            log.error("Could not list directory: " + dirPath);
        }
    }

    /**
     * Method này giúp xóa thư mục lưu các hình ảnh của dự án có mã số id
     *      * Do không rollback thao tác xóa dữ liệu trên ổ đĩa cứng, method này chỉ được gọi khi các project id được xoá thành công khỏi database
     * @param id id của dự án cần xóa
     * @param realPath đường dẫn đến thư mục cha của thư mục "WEB-INF"
     */
    public static void deleteProjecImagesFolder(Integer id, String realPath) {
        String mainDir = realPath + "WEB-INF/images/" + "project-images/" + id + "/main";
        FileUploadUtil.removeDir(mainDir);

        String projectDir = realPath + "WEB-INF/images/" + "project-images/" + id;
        FileUploadUtil.removeDir(projectDir);
    }

    /**
     * Method này giúp xóa CÁC thư mục lưu trữ hình ảnh của CÁC dự án được chọn để xóa
     * Do không rollback thao tác xóa dữ liệu trên ổ đĩa cứng, method này chỉ được gọi khi các project ids được xoá thành công khỏi database
     * @param ids danh sách các id của các dự án cần xóa
     * @param realPath đường dẫn đến thư mục cha của thư mục "WEB-INF"
     */
    public static void deleteProjecImagesFolders(List<Integer> ids, String realPath) {
        for(Integer id : ids) deleteProjecImagesFolder(id, realPath);
    }
}
