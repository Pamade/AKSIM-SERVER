package SERVER.SERVER.article;

import org.springframework.jdbc.core.JdbcTemplate;

public class ArticleDao {
    private JdbcTemplate jdbcTemplate;
    public void addArticle(Article article){
        String sql = "INSERT INTO articles VALUES(?,?,?,?,?,?,?)";
        try {
            jdbcTemplate.update(sql, article.getId(), article.getTitle(), article.getDescription(), article.getContent(), article.getImageLink(), article.getCreationDate(), article.getUserId());
        } catch (Exception e){
            e.printStackTrace();
        }


    }
}
