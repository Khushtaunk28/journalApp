package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Entity.journalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.userEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController_v2 {
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private userEntryService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUsername(userName);
        List<journalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty())
            return new ResponseEntity<>(all, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("{userName}")
    public ResponseEntity<journalEntry> createEntry(@RequestBody journalEntry entry, @PathVariable String userName) {
        try {
            journalEntryService.saveEntry(entry, userName);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("id/{myId}")
    public journalEntry getJournalEntryById(@PathVariable ObjectId myId) {
        return journalEntryService.getEntryById(myId).orElse(null);
    }

    @DeleteMapping("id/{username}/{myId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId,@PathVariable String username) {
        journalEntryService.deleteEntryById(myId,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{username}/{id}")
    public ResponseEntity<?> updateEntryById(@PathVariable String username,@PathVariable  ObjectId id, @RequestBody journalEntry entry) {
        journalEntry oldEntry = journalEntryService.getEntryById(id).orElse(null);
        if(oldEntry != null) {
            oldEntry.setTitle(entry.getTitle() != null && !entry.getTitle().equals("") ? entry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(entry.getContent() != null && !entry.getContent().equals("") ? entry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry,HttpStatus.OK);
        }

        return new ResponseEntity<>(oldEntry,HttpStatus.NOT_FOUND);
    }
}
