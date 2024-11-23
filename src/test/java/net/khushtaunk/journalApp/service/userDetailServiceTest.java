package net.khushtaunk.journalApp.service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import net.khushtaunk.journalApp.repository.userEntryRepo;
import org.springframework.security.core.userdetails.UserDetails;

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
