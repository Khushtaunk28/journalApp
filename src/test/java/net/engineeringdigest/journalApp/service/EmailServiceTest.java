package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void sendEmailTest() {
        emailService.sendEmail("2022cs_khushtaunk_b@nie.ac.in","nice bete,this auto-genearted through spring boot","Old money is a good song");
        emailService.sendEmail("2022cs_kaushalkumar_b@nie.ac.in","nice bete,this auto-genearted through spring boot","Old money is a good song");



    }
}
