package net.khushtaunk.journalApp.controller;

import net.khushtaunk.journalApp.service.SentimentalAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/journal")
public class SentimentAnalyzer {

    @Autowired
    private SentimentalAnalysisService sentimentAnalysisService;

    @PostMapping("/analyze")
    public String analyzeAllEntries() {
        sentimentAnalysisService.analyzeAllJournalEntries();
        return "Sentiment analysis completed and saved to database.";
    }
}
