package myfirst.example.plt.repository;

import myfirst.example.plt.entity.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, Long> {
        FileStorage findByHashId(String hashId);
}
