package com.anup.v1.JournalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
public class JournalAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalAppApplication.class, args);
	}

	@Bean
	public PlatformTransactionManager manageTransaction(MongoDatabaseFactory mongoDBFactory){
		return  new MongoTransactionManager(mongoDBFactory);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}

// PlatformTransactionManager : this is interface it manages and handles Transaction commits and rollback.
// MongoTransactionManager : It implements PlatformTransactionManager
// MongoDatabaseFactory : It creates the connection, manage DB connection
// SimpleMongoClientDatabaseFactory : It implements MongoDatabaseFactory
