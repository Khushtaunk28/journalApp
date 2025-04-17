package net.khushtaunk.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.khushtaunk.journalApp.Entity.User;
import net.khushtaunk.journalApp.repository.userEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class userEntryService {
    @Autowired
    public  userEntryRepo userEntryRepo ;
    private  final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    //postmap

    public  void saveEntry(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singletonList("USER"));
            userEntryRepo.save(user);
            log.info("wah bete");
        } catch (Exception e) {
            log.error("kya krte ho bhai");
            throw new RuntimeException(e);
        }
    }
    //new save fnc to encrypt pass
    public  void saveNewUser(User user) {
        userEntryRepo.save(user);
    }
    //getmap
    public List<User> getAll(){
        return userEntryRepo.findAll();

    }
    //getbyid
    public Optional<User> getEntryById(ObjectId id) {
        return userEntryRepo.findById(id);
    }
    //delete
//    public void deleteEntryById(ObjectId id) {
//          net.engineeringdigest.journalApp.repository.userEntryRepo.deleteById(id);
//    }

    //put
    public User updateEntryById(ObjectId id, User entry) {
        return userEntryRepo.save(entry);
    }
    public User findByUsername(String username) {
        return userEntryRepo.findByusername(username);
    }

    public  void  deleteByUserName(String username) {
        userEntryRepo.deleteByusername(username);
    }
    public void saveAdmin(User user) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER", "ADMIN"));
            userEntryRepo.save(user);
    }

    //controller-->service-->repository
}
