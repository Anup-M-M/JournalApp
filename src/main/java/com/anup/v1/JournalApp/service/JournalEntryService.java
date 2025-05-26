package com.anup.v1.JournalApp.service;

import com.anup.v1.JournalApp.entity.JournalEntry;
import com.anup.v1.JournalApp.entity.User;
import com.anup.v1.JournalApp.repo.JournalEntryRepository;
import org.bson.types.ObjectId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
//@Slf4j
public class JournalEntryService {

    //private static final Logger log = LoggerFactory.getLogger(JournalEntryService.class);

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;
    
    public List<JournalEntry> findAll() {
        return journalEntryRepository.findAll();
    }

    @Transactional
    public void saveEntry(JournalEntry myEntry, String userName) {
        try{
            User user = userService.findByUserName(userName);
            myEntry.setDate(LocalDateTime.now());
            JournalEntry journalEntry = journalEntryRepository.save(myEntry);
            user.getJournalEntries().add(journalEntry);
            userService.saveUser(user);
        }catch (Exception e){
            //log.error("Exception", e);
            System.out.println("Exception" + e);
            throw new RuntimeException("An exception occurred while saving the entry : " + e);
        }
    }

    public void saveEntry(JournalEntry myEntry) {
        try{
            journalEntryRepository.save(myEntry);
        }catch (Exception e){
            //log.error("Exception", e);
            System.out.println("Exception" + e);
        }
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed  = false;
        try{
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("Error occurred while deleting the entry: "+ e);
        }
        return removed;
    }
}
