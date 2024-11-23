package net.khushtaunk.journalApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.mongodb.core.index.Indexed;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Indexed(unique=true)//to create unique useername
    @NonNull //from lombok
    private String username;
    private String email;
    @Schema(description = "tell if the user want to give Analysis or not")
    private boolean sentimentAnalysis;
    @NonNull//to make sure password is not null
    private String password;
}
