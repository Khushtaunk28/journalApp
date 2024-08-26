package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Entity.journalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    private HashMap<Long, journalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public ArrayList<journalEntry> getAll() {
        return new ArrayList<>(journalEntries.values());

    }
    @PostMapping
    public boolean createEntry(@RequestBody journalEntry entry) {
    journalEntries.put(entry.getId(), entry);
    return true;

    }
    @GetMapping("id/{myId} ")
    public journalEntry getJournalEntryById(@PathVariable Long myId) {
        return journalEntries.get(myId);
    }
    @DeleteMapping("id/{myId}")
    public journalEntry deleteEntryById(@PathVariable Long myId) {
        return journalEntries.remove(myId);
    }
    @PutMapping("id/{id}")
    public journalEntry updateEntryById(@PathVariable  Long id, @RequestBody journalEntry entry) {
        return journalEntries.put(id, entry);
    }
}
