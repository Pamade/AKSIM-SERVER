package SERVER.SERVER.article;

import SERVER.SERVER.auth.AuthenticationResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ArticleService {
    private ArticleValidation articleValidation;
    public ArticleResponse addArticle(Article article){
        System.out.println(article);
        Optional<List<String>> articleErrors = articleValidation.validate(article);
        if (articleErrors.isPresent()) {
            return ArticleResponse.builder().errors(articleErrors.get()).build();
        }
        return ArticleResponse.builder().successMessage("Article added").build();
    }
}
