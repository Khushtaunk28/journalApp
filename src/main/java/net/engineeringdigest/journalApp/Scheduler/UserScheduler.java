package net.engineeringdigest.journalApp.Scheduler;

import net.engineeringdigest.journalApp.Cache.AppCache;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Entity.journalEntry;
import net.engineeringdigest.journalApp.enums.Sentiment;
import net.engineeringdigest.journalApp.repository.UserRepoImpl;
import net.engineeringdigest.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserScheduler {

    @Autowired
    private UserRepoImpl userRepo;

    @Autowired
    private AppCache appCache;


    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSendSAMail() {
        List<User> user = userRepo.getUserForSA();
        for (User u : user) {
            List<journalEntry> journalEntries = u.getJournalEntries();
            List<Sentiment> sentiments= journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentCount=new HashMap<>();
            for(Sentiment sentiment : sentiments){
                if(sentiment!=null)
                    sentimentCount.put(sentiment,sentimentCount.getOrDefault(sentiment,0)+1);

            }
            Sentiment mostFreqSentiment=null;
            int maxcount=0;
            for(Map.Entry<Sentiment,Integer> entry : sentimentCount.entrySet()){
                if(entry.getValue()>maxcount){
                    maxcount=entry.getValue();
                    mostFreqSentiment=entry.getKey();
                }
            }
            if(mostFreqSentiment!=null){
                emailService.senEmail(u.getEmail(), "Sentiment for last seven days", mostFreqSentiment.toString());
            }
        }
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void clearCache(){
        appCache.init();
        }
}
