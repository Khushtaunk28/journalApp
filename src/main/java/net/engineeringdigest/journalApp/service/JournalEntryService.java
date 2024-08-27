package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Entity.journalEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JournalEntryService {
    @Autowired
    public JournalEntryRepo journalEntryRepo;
    public void saveEntry(journalEntry entry) {
        journalEntryRepo.save(entry);
    }

    //controller-->service-->repository
}
