package tn.esprit.pidev.Services;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class ObjectStorageService implements IObjectStorageService {
    @Autowired
    AmazonS3 s3Client;
    private String doSpaceBucket = "green-bubble";

    @Override
    public String saveFileAlone(MultipartFile multipartFile, String FOLDER) {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        // String imgName =
        // FilenameUtils.removeExtension(multipartFile.getOriginalFilename());
        UUID uuid = UUID.randomUUID();
        String key = FOLDER.replaceAll(" ", "-").toLowerCase() + uuid.toString() + "." + extension;
        saveImageToServer(multipartFile, key);
        return "https://green-bubble.fra1.digitaloceanspaces.com/" + key;
    }

    @Override
    public void deleteFile(String link) {
        String key = link.replace("https://green-bubble.fra1.digitaloceanspaces.com/", "");
        // System.out.println(key);
        s3Client.deleteObject(new DeleteObjectRequest(doSpaceBucket, key));
    }

    private void saveImageToServer(MultipartFile multipartFile, String key) {
        ObjectMetadata metadata = new ObjectMetadata();
        try {
            metadata.setContentLength(multipartFile.getInputStream().available());

            if (multipartFile.getContentType() != null && !"".equals(multipartFile.getContentType())) {
                metadata.setContentType(multipartFile.getContentType());
            }
            s3Client.putObject(new PutObjectRequest(doSpaceBucket, key, multipartFile.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicReadWrite));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFiles(Set<String> fileLinks) {
        for (String file : fileLinks) {
            deleteFile(file);
        }
    }

}