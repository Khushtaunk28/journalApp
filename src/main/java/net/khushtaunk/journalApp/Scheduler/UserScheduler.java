package net.khushtaunk.journalApp.Scheduler;

import net.khushtaunk.journalApp.Cache.AppCache;
import net.khushtaunk.journalApp.Entity.User;
import net.khushtaunk.journalApp.Entity.journalEntry;
import net.khushtaunk.journalApp.enums.Sentiment;
import net.khushtaunk.journalApp.model.SentimentData;
import net.khushtaunk.journalApp.repository.UserRepoImpl;
import net.khushtaunk.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Component
public class UserScheduler {

    @Autowired
    private UserRepoImpl userRepo;

    @Autowired
    private AppCache appCache;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;



    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSendSAMail() {
        List<User> user = userRepo.getUserForSA();
        for (User u : user) {
            List<journalEntry> journalEntries = u.getJournalEntries();
            //                                                                                                      .minus(7, ChronoUnit.DAYS))
            List<Sentiment> sentiments= journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minusDays(7))).map(x -> x.getSentiment()).collect(Collectors.toList());
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
            if (mostFreqSentiment!= null) {
                SentimentData sentimentData = SentimentData.builder().email(u.getEmail()).sentiment("Sentiment for last 7 days " + mostFreqSentiment).build();
                try{
                    kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
                }catch (Exception e){
                    emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
                }
            }
        }
    }
    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearCache(){
        appCache.init();
        }
}
