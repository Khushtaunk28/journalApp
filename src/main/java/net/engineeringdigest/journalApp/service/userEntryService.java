package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.repository.userEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class userEntryService {
    @Autowired
    public  userEntryRepo userEntryRepo ;
    private  final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    //postmap
    public  void saveEntry(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userEntryRepo.save(user);
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
