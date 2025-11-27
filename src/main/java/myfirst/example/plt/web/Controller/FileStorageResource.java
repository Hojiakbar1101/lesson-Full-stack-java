package myfirst.example.plt.web.Controller;

import myfirst.example.plt.entity.FileStorage;
import myfirst.example.plt.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.UriEncoder;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/api")
public class FileStorageResource {


    @Value("${file.upload.folder}")
    private String serverFolderPath;

    private final FileStorageService fileStorageService;

    public FileStorageResource(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/fils_upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile multipartFile) {
        FileStorage fileStorage = fileStorageService.save(multipartFile);
        return ResponseEntity.ok(fileStorage);
    }

    @GetMapping("/file-preview/{hashId}")
    public ResponseEntity preview(@PathVariable String hashId) throws MalformedURLException {
        FileStorage fileStorage = fileStorageService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + UriEncoder.encode(fileStorage.getFileName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s", this.serverFolderPath, fileStorage.getUploadFolder())));

    }

    @GetMapping("/file-dowloand/{hashId}")
    public ResponseEntity dowloand(@PathVariable String hashId) throws MalformedURLException {
        FileStorage fileStorage = fileStorageService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + UriEncoder.encode(fileStorage.getFileName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s", this.serverFolderPath, fileStorage.getUploadFolder())));
    }

    @DeleteMapping("/file-delete/{hashId}")
    public ResponseEntity delete( @PathVariable String hashId){
        fileStorageService.delete(hashId);
        return ResponseEntity.ok("File muvaffaqiyatli o'chirildi");
    }

}
