package SERVER.SERVER.article;

import SERVER.SERVER.service.FileSystemStorageService;
import SERVER.SERVER.utils.URLS;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class ArticleController {
    private ArticleService articleService;
    private FileSystemStorageService storageService;
    private ArticleDao articleDao;
    private URLS urls;
    @PostMapping("/api/user/add-article")
    public ResponseEntity<ArticleResponse> addArticle(@ModelAttribute Article article) {
        return ResponseEntity.ok(articleService.addArticle(article));
    }
    @PostMapping("/api/user/add-image-to-article")
    public String addImageToArticle(@RequestParam("file") MultipartFile file) {
        String newFilename = storageService.renameFile(file);
        storageService.store(file);
        return URLS.getSERVER_URL() + "/uploads/" + newFilename;
    }

    @DeleteMapping("/api/user/remove-article/{articleID}")
    public ResponseEntity<String> removeArticle(@PathVariable int articleID) {
        return ResponseEntity.ok(articleDao.removeArticle(articleID));
    }

    @GetMapping("/api/content/get-articles")
    public ResponseEntity<Map<String, Object>> getAllArticles(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Optional<List<UserArticle>> optionalArticles = articleDao.getAllArticles(page, size);

        int totalItems = articleDao.getTotalArticlesCount();

        if (optionalArticles.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("results", optionalArticles.get());
            response.put("totalItems", totalItems);
            response.put("totalPages", (int) Math.ceil((double) totalItems / size));
            response.put("currentPage", page);
            return ResponseEntity.ok(response);
        } else return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/content/get-articles/{name}")
    public ResponseEntity<List<UserArticle>> getAllArticlesByUserEmail(@PathVariable String name){
        Optional<List<UserArticle>> optionalUserArticles = articleDao.getAllArticles(name);
        if (optionalUserArticles.isPresent()) {
            return ResponseEntity.ok(optionalUserArticles.get());
        } else return ResponseEntity.noContent().build();
    }
    @GetMapping("/api/content/get-article/{id}")
    public ResponseEntity<UserArticle> getArticle(@PathVariable long id){
        Optional<UserArticle> optionalArticle = articleDao.getArticle(id);
        if (optionalArticle.isPresent()) {
            return ResponseEntity.ok(optionalArticle.get());
        } else return ResponseEntity.noContent().build();
    }



}
