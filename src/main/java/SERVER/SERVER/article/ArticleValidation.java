package SERVER.SERVER.article;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArticleValidation extends Article{
    List<String> errors = new ArrayList<>();
    public Optional<List<String>> validate(Article article){
        if (article.getTilte().length() < 3) {
            errors.add("Title of article must contain at least 3 letters");
        }
        if (article.getContent().length() < 20) {
            errors.add("Content must be longer");
        }
        return Optional.ofNullable(errors.isEmpty() ? null : errors);

    }
}
