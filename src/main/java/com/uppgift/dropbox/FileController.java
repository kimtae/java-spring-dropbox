package com.uppgift.dropbox;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@CrossOrigin
@RestController
public class FileController {
    private static final String ROOT_PATH = System.getProperty("user.dir");

    @GetMapping(path="/file/all")
    public @ResponseBody ResponseEntity<Resource> getAllFiles (Principal principal) {
        try {
            File dir = new File(ROOT_PATH + File.separator + "userFiles" +
                    File.separator + principal.getName());

            if (dir.exists()) {
                return new ResponseEntity (dir.list(), HttpStatus.OK);
            } else {
                return new ResponseEntity ("[]", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity (e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/file")
    public @ResponseBody ResponseEntity<Resource> getFile (@RequestParam String name, Principal principal) {
        try {
            File serverFile = new File(ROOT_PATH + File.separator + "userFiles" +
                    File.separator + principal.getName() + File.separator + name);

            if (serverFile.exists()) {
                Path path = Paths.get(serverFile.getAbsolutePath());
                ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

                HttpHeaders headers = new HttpHeaders();
                headers.add("content-disposition", "attachment; filename=" + name);

                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(serverFile.length())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                return new ResponseEntity ("File Not Found", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity (e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path="/file")
    public @ResponseBody ResponseEntity<Resource> addFile (@RequestBody MultipartFile file, Principal principal) {
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

            return new ResponseEntity("File Uploaded", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path="/file")
    public @ResponseBody ResponseEntity<Resource> removeFile (@RequestParam String name, Principal principal) {
        try {
            File serverFile = new File(ROOT_PATH + File.separator + "userFiles" +
                    File.separator + principal.getName() + File.separator + name);

            if (serverFile.exists()) {
                serverFile.delete();
                return new ResponseEntity("File Deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity("File Not Found", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
