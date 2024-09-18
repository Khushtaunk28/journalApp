package net.engineeringdigest.journalApp.Entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("config_journal_app")
@Data
@NoArgsConstructor
public class ConfigJournalAppEntity {

    @NonNull
    private String key;
    private String value;

}
