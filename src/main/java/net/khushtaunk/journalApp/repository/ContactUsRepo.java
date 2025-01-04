package net.khushtaunk.journalApp.repository;

import net.khushtaunk.journalApp.Entity.ContactUs;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;


public interface ContactUsRepo extends MongoRepository<ContactUs, ObjectId> {
}
