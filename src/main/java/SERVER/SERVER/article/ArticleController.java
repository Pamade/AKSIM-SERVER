package SERVER.SERVER.article;

import SERVER.SERVER.service.FileSystemStorageService;
import SERVER.SERVER.user.User;
import SERVER.SERVER.user.UserDAO;
import SERVER.SERVER.utils.RenameFileWithExtension;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @GetMapping("/api/content/get-articles")
    public ResponseEntity<List<UserArticle>> getAllArticles() {
        Optional<List<UserArticle>> optionalArticles = articleDao.getAllArticles();
        if (optionalArticles.isPresent()) {
            System.out.println("CWEL");
            return ResponseEntity.ok(optionalArticles.get());
        } else return ResponseEntity.noContent().build();

    }

}
