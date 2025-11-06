package myfirst.example.plt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import myfirst.example.plt.entity.enummration.FileStorageEnums;

import java.io.Serializable;

@Entity
public class FileStorage implements Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String fileName;

    private String extension;

    private Long fileSize;

    private String ContentType;

    private String hasId;

    private String uploadFolder;

    private FileStorageEnums fileStorageEnum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getContentType() {
        return ContentType;
    }

    public void setContentType(String contentType) {
        ContentType = contentType;
    }

    public String getHasId() {
        return hasId;
    }

    public void setHasId(String hasId) {
        this.hasId = hasId;
    }

    public FileStorageEnums getFileStorageEnum() {
        return fileStorageEnum;
    }

    public void setFileStorageEnum(FileStorageEnums fileStorageEnum) {
        this.fileStorageEnum = fileStorageEnum;
    }


    public String getUploadFolder() {
        return uploadFolder;
    }

    public void setUploadFolder(String uploadFolder) {
        this.uploadFolder = uploadFolder;
    }
}
