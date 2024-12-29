package SERVER.SERVER.article;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ArticleDao {
    private JdbcTemplate jdbcTemplate;
    public void addArticle(Article article, long userId){
        String sql = "INSERT INTO articles (title, description, content, imageLink, creationDate, userId) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql,
                    article.getTitle(),
                    article.getDescription(),
                    article.getContent(),
                    article.getImageLink(),
                    article.getCreationDate(),
                    userId);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
