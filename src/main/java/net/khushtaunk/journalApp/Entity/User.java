package net.khushtaunk.journalApp.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document (collection="users")
@Data
public class User {
    @Id
    private ObjectId id;
    //go to application properties to tell mongodb as it dont craete automatically
    @Indexed(unique=true)//to create unique useername
//    @NonNull //from lombok
    private String username;
    private String email;
    private boolean sentimentAnalysis;
//    @NonNull//to make sure password is not null
    private String password;
    private List<String> roles;
    @DBRef//to create relation to jornal ENtry database
    private List<journalEntry> journalEntries=new ArrayList<>();
}
