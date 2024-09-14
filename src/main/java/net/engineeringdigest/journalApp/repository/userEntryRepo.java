package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component

public interface userEntryRepo extends MongoRepository<User, ObjectId> {
    User findByusername(String username);

    void deleteByusername(String username);
}
