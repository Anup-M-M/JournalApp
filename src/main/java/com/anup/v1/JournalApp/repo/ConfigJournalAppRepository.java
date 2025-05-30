package com.anup.v1.JournalApp.repo;

import com.anup.v1.JournalApp.entity.ConfigJournalAppEntity;
import com.anup.v1.JournalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, String> {
}
