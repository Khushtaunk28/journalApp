package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.service.userEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class userEntryController {
    @Autowired
    private userEntryService UserService;

    @GetMapping
    public  List<User> getAll() {
        return UserService.getAll();
    }
    @PostMapping
    public  void createuser(@RequestBody User user) {
           UserService.saveEntry(user);

    }
    @PutMapping("/{userName}")
    public ResponseEntity<?> updateuser(@RequestBody User user, @PathVariable String userName) {
        User userIndb=UserService.findByUsername(userName);
        if(userIndb!=null) {
            userIndb.setUsername(user.getUsername());
            userIndb.setPassword(user.getPassword());
            UserService.saveEntry(userIndb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
