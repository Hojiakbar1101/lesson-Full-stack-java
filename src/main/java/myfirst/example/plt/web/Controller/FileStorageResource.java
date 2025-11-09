package myfirst.example.plt.web.Controller;

import myfirst.example.plt.entity.FileStorage;
import myfirst.example.plt.service.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileStorageResource {

    private final FileStorageService fileStorageService;

    public FileStorageResource(FileStorageService fileStorageService){
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/fils_upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile multipartFile){
        FileStorage fileStorage = fileStorageService.save(multipartFile);
        return ResponseEntity.ok(fileStorage);
    }
}
