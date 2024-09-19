package net.engineeringdigest.journalApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepoImpl {
    @Autowired
    private UserRepoImpl userRepo;



   @Test
   void getUserForSA(){
        userRepo.getUserForSA();
   }
}
