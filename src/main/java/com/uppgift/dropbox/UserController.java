package com.uppgift.dropbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/user")
    public @ResponseBody User addNewUser (@RequestParam String username, @RequestParam String password) {
        User n = new User();
        n.setUsername(username);
        n.setPassword(password);
        userRepository.save(n);
        return n;
    }
}
