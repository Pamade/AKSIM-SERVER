package SERVER.SERVER.article;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Data
public class Article {

    private long id;
    private String tilte;
    private String description;
    private String content;
    private String imageLink;
    private LocalDateTime creationDate;
    private long userId;
}
