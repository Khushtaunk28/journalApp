package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Entity.journalEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    public JournalEntryRepo journalEntryRepo;
    //postmap
    public void saveEntry(journalEntry entry) {
        journalEntryRepo.save(entry);
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
    public void deleteEntryById(ObjectId id) {
          journalEntryRepo.deleteById(id);
    }
    //put
    public journalEntry updateEntryById(ObjectId id, journalEntry entry) {
        return journalEntryRepo.save(entry);
    }
    //controller-->service-->repository
}
