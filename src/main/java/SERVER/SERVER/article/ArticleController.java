package SERVER.SERVER.article;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@AllArgsConstructor
public class ArticleController {
    private ArticleService articleService;
    @PostMapping("/add-article")
    public ResponseEntity<ArticleResponse> addArticle(@RequestBody Article article) {

        return ResponseEntity.ok(articleService.addArticle(article));
    }
}