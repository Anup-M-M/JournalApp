package com.anup.v1.JournalApp.cache;

import com.anup.v1.JournalApp.entity.ConfigJournalAppEntity;
import com.anup.v1.JournalApp.repo.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys{
       WEATHER_API
    }

    public Map<String, String> apiKey;

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    @PostConstruct
    public void init(){
        apiKey = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for (ConfigJournalAppEntity configJournalAppEntity : all){
            apiKey.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }


}
