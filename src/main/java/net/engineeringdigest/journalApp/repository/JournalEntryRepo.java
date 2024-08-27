package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.Entity.journalEntry;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepo extends MongoRepository<journalEntry, String> {

}
