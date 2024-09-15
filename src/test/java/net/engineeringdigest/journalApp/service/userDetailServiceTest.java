package net.engineeringdigest.journalApp.service;
import net.engineeringdigest.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import net.engineeringdigest.journalApp.repository.userEntryRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.when;

public class userDetailServiceTest {


    @InjectMocks
    private userDetailServiceImp userDetailService;

    @Mock
    private userEntryRepo userEntryRepo;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void loadUserByUsernameTest(){
        //when(userEntryRepo.findByusername(ArgumentMatchers.anyString())).thenReturn(User.builder().username("ram").passwordEncoder("sdfdf"));
        UserDetails user=userDetailService.loadUserByUsername("ram");
        Assertions.assertNotNull(user);
    }
}
