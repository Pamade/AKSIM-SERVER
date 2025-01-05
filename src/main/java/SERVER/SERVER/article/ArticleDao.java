package SERVER.SERVER.article;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Optional<List<UserArticle>> getAllArticles() {
        String sql = "SELECT * FROM articles";
        try {
            List<UserArticle> articles = jdbcTemplate.query(sql, new UserArticleRowMapper());
            return articles.isEmpty() ? Optional.empty() : Optional.of(articles);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    public Optional<UserArticle> getArticle(long id){
        String sql = "SELECT * FROM article where id = " + id;
        try {
            UserArticle article = jdbcTemplate.queryForObject(sql, new UserArticleRowMapper());
            return Optional.of(article);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
//    public UserArticle getArticle(long id) {
//        String sql = "SELECT * from articles where id = " + id;
//    }
}
