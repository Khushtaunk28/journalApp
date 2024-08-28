package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Entity.journalEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    public JournalEntryRepo journalEntryRepo;

    @Autowired
    private userEntryService userEntryService;

    //postmap
    public void saveEntry(journalEntry entry,String userName) {
        User user=userEntryService.findByUsername(userName);
        journalEntry saved=journalEntryRepo.save(entry);
        user.getJournalEntries().add(saved);
        userEntryService.saveEntry(user);

    }
    //getmap
    public List<journalEntry> getALl(){
        return journalEntryRepo.findAll();

    }
    //getbyid
    public Optional<journalEntry> getEntryById(ObjectId id) {
        return journalEntryRepo.findById(id);
    }
    //delete
    public void deleteEntryById(ObjectId id, String username) {
        User user=userEntryService.findByUsername(username);
        user.getJournalEntries().removeIf(x->x.getId().equals(id));
        userEntryService.saveEntry(user);
        journalEntryRepo.deleteById(id);
    }
    //put
    public journalEntry updateEntryById(ObjectId id, journalEntry entry) {
        return journalEntryRepo.save(entry);
    }
    //controller-->service-->repository
}