package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.repository.userEntryRepo;
import net.engineeringdigest.journalApp.service.userEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private userEntryService UserService;
    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }
    @PostMapping("/create-user")
    public  void createuser(@RequestBody User user) {
        UserService.saveEntry(user);

    }

}
