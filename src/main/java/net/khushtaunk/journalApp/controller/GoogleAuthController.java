package net.khushtaunk.journalApp.controller;

import io.jsonwebtoken.Jwt;
import lombok.extern.slf4j.Slf4j;
import net.khushtaunk.journalApp.Entity.User;
import net.khushtaunk.journalApp.Utils.JwtUtil;
import net.khushtaunk.journalApp.repository.UserRepoImpl;
import net.khushtaunk.journalApp.repository.userEntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/auth/google")
public class GoogleAuthController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtUtil jwt;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private userEntryRepo  userEntryRepo;

    @Value("$(spring.security.oauth2.client.registration.google.client-id")
    private String clientId;

    @Value("$(spring.security.oauth2.client.registration.google.client-secret")
    private String clientsecret;


    @GetMapping("/callback")
    public ResponseEntity<?> handleGoogleCallback(@RequestParam String code) {
        try {
            String tokenendpoint = "https://oauth2.googleapis.com/token";
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);
            params.add("client_id", clientId);
            params.add("client_secret", clientsecret);
            params.add("redirect_url", "https://developers.google.com/oauthplayground");
            params.add("grant_type", "authorization_code");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<Map> tokenresponse = restTemplate.postForEntity(tokenendpoint, request, Map.class);
            String idToken = (String) tokenresponse.getBody().get("id_token");
            String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
            ResponseEntity<Map> userinforesponse = restTemplate.getForEntity(userInfoUrl, Map.class);
            if (userinforesponse.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> userInfo = userinforesponse.getBody();
                String email = (String) userInfo.get("email");
                UserDetails userDetails = null;
                try {
                    userDetails=  userDetailsService.loadUserByUsername(email);
                } catch (Exception e) {
                    if (userDetails != null) {
                        User user = new User();
                        user.setEmail(email);
                        user.setUsername(email);
                        user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                        user.setRoles(Arrays.asList("USER"));
                        userEntryRepo.save(user);
                    }
                }
                String jwttoken=jwt.generateToken(email);
                return ResponseEntity.ok(Collections.singletonMap("token",jwttoken));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }catch (Exception e) {
            log.error("Error occured while logging in Oauth");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
