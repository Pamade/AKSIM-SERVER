package SERVER.SERVER.article;

import SERVER.SERVER.user.User;
import SERVER.SERVER.user.UserDAO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth/")
@AllArgsConstructor
public class ArticleController {
    private ArticleService articleService;
    private UserDAO userDao;
    @PostMapping("/add-article")
    public ResponseEntity<ArticleResponse> addArticle(@RequestBody Article article) {


//        Long userId = user.getId();
        return ResponseEntity.ok(articleService.addArticle(article));
    }
}
