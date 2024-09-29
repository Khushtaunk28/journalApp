package net.engineeringdigest.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Utils.JwtUtil;
import net.engineeringdigest.journalApp.dto.UserDTO;
import net.engineeringdigest.journalApp.service.userEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/public")
@CrossOrigin
@Tag(name="Public Api's",description = "Check for health and first time signup and login")
public class PublicController {

    @Autowired
    private AuthenticationManager authentication;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private userEntryService UserService;
    @CrossOrigin(origins = "http://localhost:8082")
    @Operation(summary = "check if the App is working without any auth")
    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }
    @PostMapping("/sign-up")
    @Operation(summary = "Sign-up for first-time user")
    public  void signup(@RequestBody UserDTO user) {
        User newUser=new User();
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setSentimentAnalysis(user.isSentimentAnalysis());
        UserService.saveEntry(newUser);
    }
    @PostMapping("/login")
    @Operation(summary= "Login for User")
    public ResponseEntity login(@RequestBody UserDTO user) {
        User newUser=new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        try{
            authentication.authenticate(new UsernamePasswordAuthenticationToken(newUser.getUsername(), newUser.getPassword()));
            UserDetails userDetails=userDetailsService.loadUserByUsername(user.getUsername());
            String jwt=jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception while login",e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);


        }


    }

}
