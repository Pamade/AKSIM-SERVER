package SERVER.SERVER.article;

import SERVER.SERVER.auth.AuthenticationResponse;

import java.util.List;
import java.util.Optional;

public class ArticleService {
    private ArticleValidation articleValidation;
    public ArticleResponse addArticle(Article articleRequest){
        Optional<List<String>> articleErrors = articleValidation.validate(articleRequest);
        if (articleErrors.isPresent()) {
            return ArticleResponse.builder().errors(articleErrors.get()).build();
        }
        return ArticleResponse.builder().successMessage("Article added").build();
    }
}
