package com.anup.v1.JournalApp.repository;

import com.anup.v1.JournalApp.repo.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTests {

    @Autowired
    private UserRepositoryImpl userRepository;

   @Disabled("tested")
    @Test
    void testSaveNewUser() {
        Assertions.assertNotNull(userRepository.getUserForSA());
    }
}
