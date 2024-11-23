package net.khushtaunk.journalApp.repository;

import net.khushtaunk.journalApp.Entity.journalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepo extends MongoRepository<journalEntry, ObjectId> {

}
