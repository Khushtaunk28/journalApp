package net.khushtaunk.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.khushtaunk.journalApp.Entity.User;
import net.khushtaunk.journalApp.Entity.journalEntry;
import net.khushtaunk.journalApp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {
    @Autowired
    public JournalEntryRepo journalEntryRepo;

    @Autowired
    private userEntryService userEntryService;
   // private final Logger logger= LoggerFactory.getLogger(JournalEntryService.class);

    //postmap

   @Transactional//operate as single unit,if anyone of the proecss fails ,then rollback
   public void saveEntry(journalEntry journalEntry, String userName) {
       try {
           User user = userEntryService.findByUsername(userName);
           journalEntry.setDate(LocalDateTime.now());
           journalEntry saved = journalEntryRepo.save(journalEntry);
           user.getJournalEntries().add(saved);
           userEntryService.saveNewUser(user);
       } catch (Exception e) {
           throw new RuntimeException("An error occurred while saving the entry.", e);
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
