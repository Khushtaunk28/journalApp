package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import net.engineeringdigest.*;
import net.engineeringdigest.journalApp.repository.userEntryRepo;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class userEntryServiceTest  {
    @Autowired
    private userEntryRepo userEntryRepo;

    //@Disabled
    @Test
    public void testFindByUsername() {
        assertEquals(4,2+2);
        User user = userEntryRepo.findByusername("vipul");
        assertNotNull(userEntryRepo.findByusername("khush"),"failed for message");
        assertTrue(user.getJournalEntries() != null);
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,9"
    })
    public void test(int a,int b,int expected) {
        assertEquals(expected,a+b);

    }




}
