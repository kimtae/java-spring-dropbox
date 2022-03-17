package com.uppgift.dropbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;

@CrossOrigin
@RestController
public class FileController {
    @Autowired
    private FileRepository fileRepository;

    private static final String ROOT_PATH = System.getProperty("user.dir");

    @PostMapping(path="/file")
    public @ResponseBody String addFile (@RequestBody MultipartFile file, Principal principal) {
        try {
            // Create directory for saving files if necessary
            File dir = new File(ROOT_PATH + File.separator + "userFiles" +
                    File.separator + principal.getName());
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Create the file on server
            byte[] bytes = file.getBytes();
            File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();

            return "Success";
        } catch (Exception e) {
            return e.toString();
        }
    }
}
