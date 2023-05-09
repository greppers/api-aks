package tn.esprit.pidev.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.pidev.Services.IObjectStorageService;

@RestController
@RequestMapping("/api/storage/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ObjectStorageController {
    @Autowired
    IObjectStorageService objectStorageService;

    @DeleteMapping("files")
    public String deleteFile(@RequestBody List<String> files) {
        if(files!=null)
        files.forEach(file -> {System.out.println(file.toString());objectStorageService.deleteFile(file);});

        return "done";
    }
}
