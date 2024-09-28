package net.engineeringdigest.journalApp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.apiResponse.WeatherApiResponse;
import net.engineeringdigest.journalApp.service.userEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import net.engineeringdigest.journalApp.service.weatherService;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name="User APi's",description = "Read update and delete User")
public class userEntryController {
    @Autowired
    private weatherService weatherService;
    @Autowired
    private userEntryService UserService;
    @Autowired
    private net.engineeringdigest.journalApp.repository.userEntryRepo userEntryRepo;

//    @GetMapping
//    public  List<User> getAll() {
//        return UserService.getAll();
//    }

    @PutMapping()
    public ResponseEntity<?> updateuser(@RequestBody User user) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();

        User userIndb=UserService.findByUsername(username);
            userIndb.setUsername(user.getUsername());
            userIndb.setPassword(user.getPassword());
            userIndb.setEmail(user.getEmail());
            userIndb.setSentimentAnalysis(user.isSentimentAnalysis());
            UserService.saveEntry(userIndb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteuserById() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        UserService.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> showWeather(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        WeatherApiResponse response=weatherService.getWeather("Patna");
        String greetings=" ";
        if(response!=null){
            greetings=",Weather feels like"+response.getCurrent().getFeelslike();
        }

        return new ResponseEntity<>("HI"+authentication.getName()+greetings,HttpStatus.OK);
    }



}
