package net.khushtaunk.journalApp.service;

import net.khushtaunk.journalApp.Entity.ContactUs;
import net.khushtaunk.journalApp.repository.ContactUsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ContactusService {
    @Autowired
    public ContactUsRepo contactUsrepo;

    public void saveEntry(ContactUs data){
        contactUsrepo.save(data);
    }
}
