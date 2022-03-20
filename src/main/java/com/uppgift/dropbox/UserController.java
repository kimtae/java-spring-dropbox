package com.uppgift.dropbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/user")
    public @ResponseBody User getUser (@RequestParam String username) {
        return userRepository.findByUsername(username);
    }

    @PostMapping(path="/user")
    public @ResponseBody ResponseEntity<Resource> addUser (@RequestParam String username, @RequestParam String password) {
        if (userRepository.findByUsername(username) != null) {
            return new ResponseEntity("User Already Exists", HttpStatus.OK);
        } else {
            User user = new User();
            user.setUsername(username);
            user.setPassword(new BCryptPasswordEncoder(10).encode(password));
            userRepository.save(user);
            return new ResponseEntity("User Created", HttpStatus.OK);
        }
    }
}
