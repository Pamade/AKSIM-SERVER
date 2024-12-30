package SERVER.SERVER.article;

import SERVER.SERVER.service.FileSystemStorageService;
import SERVER.SERVER.user.User;
import SERVER.SERVER.user.UserDAO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth/")
@AllArgsConstructor
public class ArticleController {
    private ArticleService articleService;
    @PostMapping("/add-article")
    public ResponseEntity<ArticleResponse> addArticle(@ModelAttribute Article article) {
        return ResponseEntity.ok(articleService.addArticle(article));
    }
}
