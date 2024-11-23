package net.khushtaunk.journalApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import net.khushtaunk.journalApp.enums.Sentiment;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalDTO {
    @NonNull
    private String title;
    private String content;
    @Schema(description = "Tell the sentiment out of the listed Below /n HAPPY,\n" +
            "    SAD,\n" +
            "    ANGRY,\n" +
            "    ANXIOUS")
    private Sentiment sentiment;

}
