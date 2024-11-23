package net.khushtaunk.journalApp.repository;

import net.khushtaunk.journalApp.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component

public interface userEntryRepo extends MongoRepository<User, ObjectId> {
    User findByusername(String username);

    void deleteByusername(String username);
}
