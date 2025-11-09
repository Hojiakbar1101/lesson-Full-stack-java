package myfirst.example.plt.service;

import myfirst.example.plt.entity.FileStorage;
import myfirst.example.plt.entity.enummration.FileStorageEnums;
import myfirst.example.plt.repository.FileStorageRepository;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class FileStorageService {

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

        Date now = new Date();
        String path = String.format("%s/upload_files/%d/%d/%d",
                this.serverFolderPath,
                1900 + now.getYear(),
                1 + now.getMonth(),
                now.getDate());

        File uploadFolder = new File(path);

        if (!uploadFolder.exists() && uploadFolder.mkdirs()) {
            System.out.println("Upload papkasi yaratildi: " + uploadFolder.getAbsolutePath());
        }

        fileStorage.setHasId(hashids.encode(fileStorage.getId()));

        String pathLocal = String.format("/upload_files/%d/%d/%d/%s.%s",
                1900 + now.getYear(),
                1 + now.getMonth(),
                now.getDate(),
                fileStorage.getHasId(),
                fileStorage.getExtension());

        fileStorage.setUploadFolder(pathLocal);
        fileStorageRepository.save(fileStorage);

        File file = new File(uploadFolder, String.format("%s.%s",
                fileStorage.getHasId(),
                fileStorage.getExtension()));

        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileStorage;
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
