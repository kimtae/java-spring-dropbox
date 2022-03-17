package com.uppgift.dropbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
public class FileController {
    @Autowired
    private FileRepository fileRepository;

    @PostMapping(path="/file")
    public @ResponseBody String addFile (@RequestBody MultipartFile file) {
        try {
            System.out.println(file.getContentType());
            return null;
        } catch (Exception e) {
            return e.toString();
        }
    }
}
