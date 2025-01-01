package SERVER.SERVER.article;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Service
public class Article {
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("content")
    private String content;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonProperty("creationDate")
    private Date creationDate;
    @JsonProperty("imageLink")
    private String imageLink;

}
