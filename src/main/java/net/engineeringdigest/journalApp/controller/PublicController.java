package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Utils.JwtUtil;
import net.engineeringdigest.journalApp.repository.userEntryRepo;
import net.engineeringdigest.journalApp.service.userEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private AuthenticationManager authentication;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private userEntryService UserService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }
    @PostMapping("/sign-up")
    public  void signup(@RequestBody User user) {
        UserService.saveEntry(user);

    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        try{
            authentication.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails=userDetailsService.loadUserByUsername(user.getUsername());
            String jwt=jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception while login",e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);


        }


    }

}
