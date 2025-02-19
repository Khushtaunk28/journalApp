package net.khushtaunk.journalApp.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "contact_us")
@NoArgsConstructor
@Data
public class ContactUs {
    @Id
    private ObjectId id;

    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String query;

}
