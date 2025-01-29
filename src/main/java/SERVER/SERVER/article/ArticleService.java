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
import java.util.*;

@AllArgsConstructor
@Service
public class ArticleService {
    private ArticleValidation articleValidation;
    private ArticleDao articleDao;
    private FileSystemStorageService storageService;

    public ArticleResponse addArticle(Article article){

        Map<String, String> validationResults = validationErrors(article);
        boolean isValid = validationResults.containsKey("error") || validationResults.containsKey("title") || validationResults.containsKey("content");
        if (!isValid) {
            String userId = validationResults.get("userId");
            String userName = validationResults.get("username");
            articleDao.addArticle(article, Long.parseLong(userId), userName);
            return ArticleResponse.builder().successMessage("Article added").build();
        } else {
            return ArticleResponse.builder().errors(validationResults).build();
        }
    }

    public ArticleResponse updateArticle(UserArticle article) {

        Map<String, String> validationResults = validationErrors(article);
        boolean hasErrors = validationResults.containsKey("error") || validationResults.containsKey("title") || validationResults.containsKey("content");

        if (!hasErrors) {
            System.out.println("should go");
            articleDao.updateArticle(article);
            return ArticleResponse.builder().successMessage("Article added").build();
        } else {
            return ArticleResponse.builder().errors(validationResults).build();
        }
    }

    private Map<String, String> validationErrors(Article article){
        long userId;
        String userName;
        Optional<Map<String, String>> articleErrors = articleValidation.validate(article);
        Map<String, String> userValidate = articleValidation.validate();
        Map<String, String> userData = new HashMap<>();

        if (userValidate.containsKey("error")) {
            return userValidate;
        } else {
            userId = Long.parseLong(userValidate.get("userId"));
            userName = userValidate.get("username");
            userData.put("userId", String.valueOf(userId));
            userData.put("username", userName);
        }

        if (article.getImageFile() != null) {
            MultipartFile file = article.getImageFile();
            String path = storageService.store(file);
            article.setImageLink(path);
        }

        if (articleErrors.isPresent()) {
            return articleErrors.get();
        }
//        articleDao.addArticle(article, userId, userName);
//        I WANT TO MAKE THE SAME LOGIC BUT HERE USE UPDATE INSTEAD OF ADD
        return userData;
    }

}
