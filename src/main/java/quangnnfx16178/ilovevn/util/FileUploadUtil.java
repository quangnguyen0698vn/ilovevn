package quangnnfx16178.ilovevn.util;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

// https://www.codejava.net/frameworks/spring-boot/spring-boot-file-upload-tutorial

@Log4j2
public class FileUploadUtil {
    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile)  {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
                log.info("Create folder successfully: " + uploadPath);
            } catch (IOException e) {
                log.error("Cannot create folder " + uploadPath, e);
            }
        }

        Path filePath = uploadPath.resolve(fileName);
        File destinationFile = filePath.toFile();
        try {
            FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream(destinationFile));
            log.info("Copy file successfully: " + fileName);
        } catch (IOException e) {
            log.error("Copy file failed: " + fileName, e);
        }

    }

    public static void cleanDir(String dir) {
        Path dirPath = Paths.get(dir);
        if (!Files.exists(dirPath)) return;
        try {
            Files.list(dirPath).forEach(file -> {
             if (!Files.isDirectory(file)) {
                    try {
                        Files.delete(file);
                        log.info("Delete file successfully: " + file);
                    } catch (IOException ex) {
                        log.error("Could not delete file: " + file);
                    }
                }
            });
        } catch (IOException e) {
            log.error("Could not list directory: " + dirPath, e);
        }
    }

    public static void removeDir(String dir) {
        cleanDir(dir);

        try {
            Files.delete(Paths.get(dir));
            log.info("remove directory successfully " + dir);
        } catch (IOException e) {
            log.error("Could not remove directory: " + dir, e);
        }
    }
}
