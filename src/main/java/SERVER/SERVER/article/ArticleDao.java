package SERVER.SERVER.article;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ArticleDao {
    private JdbcTemplate jdbcTemplate;
    public void addArticle(Article article, long userId){
        String sql = "INSERT INTO articles (title, description, content, creationDate,imageLink, userId) VALUES (?, ?, ?,  ?, ?, ?)";
        try {
            jdbcTemplate.update(sql,
                    article.getTitle(),
                    article.getDescription(),
                    article.getContent(),
                    article.getCreationDate(),
                    article.getImageLink(),
                    userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
