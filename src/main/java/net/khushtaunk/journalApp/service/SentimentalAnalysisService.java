package net.khushtaunk.journalApp.service;

import net.khushtaunk.journalApp.Entity.journalEntry;
import net.khushtaunk.journalApp.repository.JournalEntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SentimentalAnalysisService {

    @Autowired
    private JournalEntryRepo journalEntryRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String pythonApiUrl = "http://localhost:5000/predict"; // Python API URL

    public void analyzeAllJournalEntries() {
        // Fetch all journal entries
        List<journalEntry> journalEntries = journalEntryRepository.findAll();
        for (journalEntry entry : journalEntries) {
            if (entry.getContent() != null) {
                // Send content to Python API
                String sentiment = analyzeSentiment(entry.getContent());
                if (sentiment != null) {
                    // Update sentiment in the database
                    entry.setSentiment(sentiment);
                    journalEntryRepository.save(entry); // Save updated entry
                }
            }
        }
    }

    public String analyzeSentiment(String text) {
        // Prepare request payload
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"text\": \"" + text + "\"}", headers);

        try {
            // Send POST request to Python API
            ResponseEntity<String> response = restTemplate.postForEntity(pythonApiUrl, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody(); // Sentiment returned by Python API
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
