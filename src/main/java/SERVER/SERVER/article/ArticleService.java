package SERVER.SERVER.article;

import SERVER.SERVER.auth.AuthenticationResponse;
import SERVER.SERVER.service.FileSystemStorageService;
import SERVER.SERVER.service.StorageService;
import SERVER.SERVER.user.User;
import SERVER.SERVER.user.UserDAO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ArticleService {
    private ArticleValidation articleValidation;
    private ArticleDao articleDao;
    private FileSystemStorageService storageService;
    public ArticleResponse addArticle(Article article){
        long userId;
        Optional<Map<String, String>> articleErrors = articleValidation.validate(article);

        Map<String, String> userValidate = articleValidation.validate();

        if (userValidate.containsKey("error")) {
            return ArticleResponse.builder().errors(userValidate).build();
        } else {
            userId = Long.parseLong(userValidate.get("userId"));
        }
        if (articleErrors.isPresent()) {
            return ArticleResponse.builder().errors(articleErrors.get()).build();
        }
        articleDao.addArticle(article, userId);
        return ArticleResponse.builder().successMessage("Article added").build();
    }


}
