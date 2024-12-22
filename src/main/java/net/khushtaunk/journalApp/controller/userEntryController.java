package net.khushtaunk.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.khushtaunk.journalApp.Entity.User;
import net.khushtaunk.journalApp.apiResponse.WeatherApiResponse;
import net.khushtaunk.journalApp.dto.UserDTO;
import net.khushtaunk.journalApp.service.userEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import net.khushtaunk.journalApp.service.WeatherService;

@RestController
@RequestMapping("/user")
@Tag(name="User APi's",description = "Read update and delete User")
public class userEntryController {
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private userEntryService UserService;
    @Autowired
    private net.khushtaunk.journalApp.repository.userEntryRepo userEntryRepo;

//    @GetMapping
//    public  List<User> getAll() {
//        return UserService.getAll();
//    }

    @PutMapping()
    @Operation(summary = "Update-edit user details with Updated details input")
    public ResponseEntity<?> updateuser(@RequestBody UserDTO user) {
        User newUser=new User();
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setSentimentAnalysis(user.isSentimentAnalysis());
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User userIndb=UserService.findByUsername(username);
            userIndb.setUsername(newUser.getUsername());
            userIndb.setPassword(newUser.getPassword());
            userIndb.setEmail(newUser.getEmail());
            userIndb.setSentimentAnalysis(newUser.isSentimentAnalysis());
            UserService.saveEntry(userIndb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    

    @DeleteMapping("/user")
    @Operation(summary = "Delete user by its id")
    public ResponseEntity<?> deleteuserById() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        UserService.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Get the current Weather Of your City")
    public ResponseEntity<?> showWeather(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        WeatherApiResponse response=weatherService.getWeather("Mysore");
        String greetings=" ";
        if(response!=null){
            greetings=",Weather feels like"+response.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi  "+ "Mr/Mrs."+authentication.getName()+greetings,HttpStatus.OK);
    }



}
