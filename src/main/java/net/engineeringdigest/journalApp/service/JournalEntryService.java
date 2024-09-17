package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Entity.journalEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    public JournalEntryRepo journalEntryRepo;

    @Autowired
    private userEntryService userEntryService;
    private final Logger logger= LoggerFactory.getLogger(JournalEntryService.class);

    //postmap
   @Transactional//operate as single unit,if anyone of the proess fails ,then rollback
    public void saveEntry(journalEntry entry,String userName) {
       try {
           User user = userEntryService.findByUsername(userName);
           //journalEntry.setDate(LocalDateTime.now());
           journalEntry saved = journalEntryRepo.save(entry);
           user.getJournalEntries().add(saved);
           userEntryService.saveNewUser(user);
       }catch (Exception e) {
           logger.info("using logger for first time");
           //System.out.println(e);
           throw new RuntimeException("AN error oocured",e);
       }

    }
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
    @Transactional
    public boolean deleteEntryById(ObjectId id, String username) {
       boolean removed=false;
       try {
           User user = userEntryService.findByUsername(username);
           removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
           if (removed) {
               userEntryService.saveNewUser(user);
               journalEntryRepo.deleteById(id);
           }

       }catch (Exception e) {
           System.out.println(e);
           throw new RuntimeException("AN error oocured while deleting",e);
       }
       return removed;
    }
    //put
    public journalEntry updateEntryById(ObjectId id, journalEntry entry) {
        return journalEntryRepo.save(entry);
    }
    //controller-->service-->repository
}
