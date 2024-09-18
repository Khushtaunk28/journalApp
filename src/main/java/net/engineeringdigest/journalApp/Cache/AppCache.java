package net.engineeringdigest.journalApp.Cache;


import net.engineeringdigest.journalApp.Entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.repository.ConfigJournalAppRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigJournalAppRepo configJournalAppRepo;
    public  Map<String,String> APP_CACHE= new HashMap<String,String>();

    @PostConstruct
    public void init(){
        List<ConfigJournalAppEntity> all=configJournalAppRepo.findAll();
        for(ConfigJournalAppEntity app:all) {
            APP_CACHE.put(app.getKey(), app.getValue());
        }
    }


}
