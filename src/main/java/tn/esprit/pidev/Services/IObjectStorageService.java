package tn.esprit.pidev.Services;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

public interface IObjectStorageService {
    String saveFileAlone(MultipartFile multipartFile, String FOLDER);
    void deleteFile(String fileLink);
    void deleteFiles(Set<String>fileLinks);
}