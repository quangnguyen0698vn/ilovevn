package quangnnfx16178.ilovevn.project;

import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import quangnnfx16178.ilovevn.entity.Charity;
import quangnnfx16178.ilovevn.util.FileUploadUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Log4j2
public class ProjectSaveHelper {

    public static void setName(Project project, String name) {
        project.setName(name);
    }

    public static void setCharity(Project project, Charity charity) {
        project.setCharity(charity);
    }

    public static void setAmount(Project project, Long targetAmount) {
        project.setTargetAmount(targetAmount);
    }

    public static void setStartedDate(Project project, Date startedDate) {
        project.setStartedDate(startedDate);
    }

    public static void setExpiredDate(Project project, Date expiredDate) {
        project.setExpiredDate(expiredDate);
    }

    public static void setShortDescription(Project project, String shortDescription) {
        project.setShortDescription(shortDescription);
    }

    public static void setFullDescription(Project project, String fullDescription) {
        project.setFullDescription(fullDescription);
    }

    public static void setId(Project project, Integer id) {
        project.setId(id);
    }

    public static void setMainImageName(Project project, MultipartFile mainImageMultipart) {
        if (mainImageMultipart != null && !mainImageMultipart.isEmpty()) {
            String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
            project.setMainImage(fileName);
        }
    }

    public static void removeDeletedProjectImages(Project project, String[] imageIDs, String[] imageNames) {
        if (imageIDs == null || imageIDs.length == 0) return;
        Set<String> existingFileNames = new HashSet<>();
        for (int i = 0; i < imageIDs.length; i++) {
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

    public static void addNewProjectImage(Project project, MultipartFile[] projectImageMultiparts) {
        if (projectImageMultiparts == null || projectImageMultiparts.length == 0) return;
        for (MultipartFile image : projectImageMultiparts) {
            if (image.isEmpty()) continue;
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            project.getImages().add(new ProjectImage(project, fileName));
        }
    }


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
}
