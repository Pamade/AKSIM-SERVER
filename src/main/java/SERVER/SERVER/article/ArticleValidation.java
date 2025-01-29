package SERVER.SERVER.article;

import SERVER.SERVER.user.User;
import SERVER.SERVER.user.UserDAO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ArticleValidation extends Article{
    private UserDAO userDao;
    public Optional<Map<String, String>> validate(Article article){

        Map<String, String> errors = new HashMap<>();

        if (article.getTitle() == null || article.getTitle().length() < 3) {
            errors.put("title", "Title of article must contain at least 3 letters");
        }
        assert article.getTitle() != null;
        if (article.getTitle().length() > 255) {
            errors.put("title", "Article Title too long");
        }
        if (article.getContent() == null || article.getContent().length() < 20) {
            errors.put("content", "Content must be longer");
        }
//        errors.add("Content must be longer");
        return Optional.ofNullable(errors.isEmpty() ? null : errors);
    }
    public Map<String, String> validate() {
        Map<String, String> result = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userDao.findByEmail(username);  // userDao should return Optional<User>

        if (userOptional.isEmpty()) {
            result.put("error", "Wrong user");
        } else {
            User user = userOptional.get();
            result.put("userId", String.valueOf(user.getId()));
            result.put("username", user.getName());
        }

        return result;
    }

}
