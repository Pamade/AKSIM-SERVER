package SERVER.SERVER.article;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@Service
public class Article {
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("content")
    private String content;
    @JsonProperty("creationDate")
    private OffsetDateTime creationDate;
    @JsonProperty("imageFile")
    private MultipartFile imageFile;
    @JsonProperty("imageLink")
    private String imageLink;
}
