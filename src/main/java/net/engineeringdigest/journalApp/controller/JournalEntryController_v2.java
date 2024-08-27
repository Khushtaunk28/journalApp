package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Entity.journalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/journal")
public class JournalEntryController_v2 {
    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ArrayList<journalEntry> getAll() {
        return null;
    }
    @PostMapping
    public boolean createEntry(@RequestBody journalEntry entry) {
        journalEntryService.saveEntry(entry);
    return true;

    }
    @GetMapping("id/{myId} ")
    public journalEntry getJournalEntryById(@PathVariable Long myId) {
        return null;
    }
    @DeleteMapping("id/{myId}")
    public journalEntry deleteEntryById(@PathVariable Long myId) {
        return null;
    }
    @PutMapping("id/{id}")
    public journalEntry updateEntryById(@PathVariable  Long id, @RequestBody journalEntry entry) {
        return null;
    }
}
