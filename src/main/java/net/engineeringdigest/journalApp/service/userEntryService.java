package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Entity.journalEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepo;
import net.engineeringdigest.journalApp.repository.userEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class userEntryService {
    @Autowired
    public  userEntryRepo userEntryRepo ;
    //postmap
    public  void saveEntry(User user) {
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
    //controller-->service-->repository
}
