package myfirst.example.plt.repository;

import myfirst.example.plt.entity.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, Long> {

}
