package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Entity.journalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<journalEntry> getAll() {
        return journalEntryService.getALl();
    }
    @PostMapping
    public journalEntry createEntry(@RequestBody journalEntry entry) {
        entry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(entry);
        return entry;
    }
    @GetMapping("id/{myId} ")
    public journalEntry getJournalEntryById(@PathVariable ObjectId myId) {
       return journalEntryService.getEntryById(myId).orElse(null);
    }
    @DeleteMapping("id/{myId}")
    public boolean deleteEntryById(@PathVariable ObjectId myId) {
         journalEntryService.deleteEntryById(myId);
         return true;
    }
    @PutMapping("id/{id}")
    public journalEntry updateEntryById(@PathVariable  ObjectId id, @RequestBody journalEntry entry) {
        journalEntry oldEntry = journalEntryService.getEntryById(id).orElse(null);
        if(oldEntry != null) {
            oldEntry.setTitle(entry.getTitle() != null && !entry.getTitle().equals("") ? entry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(entry.getContent() != null && !entry.getContent().equals("") ? entry.getContent() : oldEntry.getContent());
        }
            journalEntryService.saveEntry(oldEntry);
            return oldEntry;
    }
}
