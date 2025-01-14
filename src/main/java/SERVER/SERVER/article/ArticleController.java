package SERVER.SERVER.article;

import SERVER.SERVER.service.FileSystemStorageService;
import SERVER.SERVER.utils.RenameFileWithExtension;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("/api/user/")
@AllArgsConstructor
public class ArticleController {
    private ArticleService articleService;
    private FileSystemStorageService storageService;
    private ArticleDao articleDao;
    @PostMapping("/api/user/add-article")
    public ResponseEntity<ArticleResponse> addArticle(@ModelAttribute Article article) {
        return ResponseEntity.ok(articleService.addArticle(article));
    }
    @PostMapping("/api/user/add-image-to-article")
    public String addImageToArticle(@RequestParam("file") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String newFilename = RenameFileWithExtension.rename(originalFilename);
        storageService.store(file);
        return "http://localhost:8080/uploads/" + newFilename;
    }

    @DeleteMapping("/api/user/remove-article/{articleID}")
    public ResponseEntity<String> removeArticle(@PathVariable int articleID) {
        return ResponseEntity.ok(articleDao.removeArticle(articleID));
    }

    @GetMapping("/api/content/get-articles")
    public ResponseEntity<List<UserArticle>> getAllArticles() {
        Optional<List<UserArticle>> optionalArticles = articleDao.getAllArticles();
        if (optionalArticles.isPresent()) {
            return ResponseEntity.ok(optionalArticles.get());
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
