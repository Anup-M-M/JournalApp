package com.anup.v1.JournalApp.scheduler;

import com.anup.v1.JournalApp.cache.AppCache;
import com.anup.v1.JournalApp.constants.Sentiment;
import com.anup.v1.JournalApp.entity.JournalEntry;
import com.anup.v1.JournalApp.entity.User;
import com.anup.v1.JournalApp.model.SentimentData;
import com.anup.v1.JournalApp.repo.UserRepositoryImpl;
import com.anup.v1.JournalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;


    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepository.getUserForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream()
                    .filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null)
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null) {
                    //emailService.sendEmail(user.getEmail(), "Sentiment for previous week", mostFrequentSentiment.toString());
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days " + mostFrequentSentiment).build();
                  try{
                    kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
                  }catch (Exception e){
                    emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
                  }
                }
            }
        }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        appCache.init();
    }
}
