package net.engineeringdigest.journalApp.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "journal_Entries")
@Data
public class journalEntry {
    @Id
    private ObjectId id;
    private LocalDateTime date;
    private String title;
    private String content;

//    public String getTitle() {
//        return title;
//    }

//    public void setTitle(String title) {
//        this.title = title;
//    }
//    public LocalDateTime getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDateTime date) {
//        this.date = date;
//    }
//
//
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public ObjectId getId() {
//        return id;
//    }
//
//    public void setId(ObjectId id) {
//        this.id = id;
//    }
//

}
