package SERVER.SERVER.article;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;


@Getter
@Setter
@Service
public class UserArticle extends Article{
    private long id;
    private long userID;
}
