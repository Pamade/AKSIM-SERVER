package SERVER.SERVER.article;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ArticleValidation extends Article{
    List<String> errors = new ArrayList<>();
    public Optional<List<String>> validate(Article article){
        System.out.println(article.toString() + "To jest article");
        if (article.getTitle().length() < 3) {
            errors.add("Title of article must contain at least 3 letters");
        }
        if (article.getContent().length() < 20) {
            errors.add("Content must be longer");
        }
//        errors.add("Content must be longer");
        return Optional.ofNullable(errors.isEmpty() ? null : errors);

    }
}
