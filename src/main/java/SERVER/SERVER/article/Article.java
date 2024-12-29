package SERVER.SERVER.article;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Data
@Service
public class Article {
    @JsonProperty("id")
    private long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("content")
    private String content;
    @JsonProperty("imageLink")
    private String imageLink;
    @JsonProperty("creationDate")
    private LocalDateTime creationDate;
    @JsonProperty("userId")
    private long userId;

}
