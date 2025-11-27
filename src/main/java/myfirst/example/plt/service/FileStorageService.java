package myfirst.example.plt.service;

import myfirst.example.plt.entity.FileStorage;
import myfirst.example.plt.entity.enummration.FileStorageEnums;
import myfirst.example.plt.repository.FileStorageRepository;
import org.hashids.Hashids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Service
public class FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    private final FileStorageRepository fileStorageRepository;

    @Value("${file.upload.folder}")
    private String serverFolderPath;

    private final Hashids hashids;

    public FileStorageService(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
        this.hashids = new Hashids();
    }

    public FileStorage save(MultipartFile multipartFile) {
        FileStorage fileStorage = new FileStorage();
        fileStorage.setFileName(multipartFile.getOriginalFilename());
        fileStorage.setFileSize(multipartFile.getSize());
        fileStorage.setContentType(multipartFile.getContentType());
        fileStorage.setExtension(getExt(multipartFile.getOriginalFilename()));
        fileStorage.setFileStorageEnum(FileStorageEnums.Draft);
        fileStorage = fileStorageRepository.save(fileStorage);

        // ✔ DEPRECATED metodlar o‘rniga LocalDate
        LocalDate now = LocalDate.now();

        String path = String.format("%s/upload_files/%d/%d/%d",
                this.serverFolderPath,
                now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth());

        File uploadFolder = new File(path);

        if (!uploadFolder.exists() && uploadFolder.mkdirs()) {
            logger.info("Upload papkasi yaratildi: {}", uploadFolder.getAbsolutePath());
        }

        fileStorage.setHashId(hashids.encode(fileStorage.getId()));

        String pathLocal = String.format("/upload_files/%d/%d/%d/%s.%s",
                now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth(),
                fileStorage.getHashId(),
                fileStorage.getExtension());

        fileStorage.setUploadFolder(pathLocal);
        fileStorageRepository.save(fileStorage);

        File file = new File(uploadFolder, String.format("%s.%s",
                fileStorage.getHashId(),
                fileStorage.getExtension()));

        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            logger.error("Faylni saqlashda xatolik: ", e);
        }

        return fileStorage;
    }

    public FileStorage findByHashId(String hashId){
        return fileStorageRepository.findByHashId(hashId);
    }

    public void delete(String hashId){
        FileStorage fileStorage = fileStorageRepository.findByHashId(hashId);
        File file = new File(String.format("%s/%s", this.serverFolderPath, fileStorage.getUploadFolder()));

        if (file.exists()) {
            fileStorageRepository.delete(fileStorage);
        }
    }

    private String getExt(String fileName) {
        if (fileName != null && !fileName.isEmpty()) {
            int dot = fileName.lastIndexOf('.');
            if (dot > 0 && dot < fileName.length() - 1) {
                return fileName.substring(dot + 1);
            }
        }
        return null;
    }
}
