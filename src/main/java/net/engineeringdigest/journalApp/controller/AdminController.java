package net.engineeringdigest.journalApp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.service.userEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name="Admin APi's",description = "admin can access all user data and create new admin")
public class AdminController {
    @Autowired
    private userEntryService userService;



@GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> all = userService.getAll();
        if(!all.isEmpty()&& all!=null){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
@PostMapping("/create-admin-user")
    public void  createAdminUser(@RequestBody User user){
    userService.saveAdmin(user);
}

}
