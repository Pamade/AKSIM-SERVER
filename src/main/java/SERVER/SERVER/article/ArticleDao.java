package SERVER.SERVER.article;

import SERVER.SERVER.user.User;
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
    public void addArticle(Article article, long userId, String userName){
        String sql = "INSERT INTO articles (title, description, content, creationDate,imageLink,userName, userId) VALUES (?, ?, ?,  ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql,
                    article.getTitle(),
                    article.getDescription(),
                    article.getContent(),
                    article.getCreationDate(),
                    article.getImageLink(),
                    userName,
                    userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Optional<List<UserArticle>> getAllArticles(int page, int size) {
        String sql = "SELECT * FROM articles ORDER BY creationDate DESC LIMIT ? OFFSET ?";
        try {
            int offset = (page - 1) * size;
            List<UserArticle> articles = jdbcTemplate.query(sql, new Object[]{size, offset}, new UserArticleRowMapper());
            return articles.isEmpty() ? Optional.empty() : Optional.of(articles);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public int getTotalArticlesCount () {
        String sql = "SELECT COUNT(*) FROM articles";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public Optional<List<UserArticle>> getAllArticles(String name) {
        String sql = "SELECT * from articles where userName = ?";
        System.out.println(name);
        try {
            List<UserArticle> articles = jdbcTemplate.query(sql, new Object[]{name}, new UserArticleRowMapper());
            return articles.isEmpty() ? Optional.empty() : Optional.of(articles);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    public Optional<UserArticle> getArticle(long id){
        String sql = "SELECT * FROM articles where id = " + id;
        try {
            UserArticle article = jdbcTemplate.queryForObject(sql, new UserArticleRowMapper());
            return Optional.of(article);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public String removeArticle(int articleID){
        String sql = "DELETE FROM articles WHERE ID = " + articleID;
        try {
            jdbcTemplate.execute(sql);
            return "Article removed";
        } catch (Exception e) {
            return "Removing article failed";
        }
    }
}
