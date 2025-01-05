package SERVER.SERVER.article;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserArticleRowMapper implements RowMapper<UserArticle> {
    @Override
    public UserArticle mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserArticle article = new UserArticle();
        article.setId(rs.getLong("id"));
        article.setTitle(rs.getString("title"));
        article.setContent(rs.getString("content"));
        article.setDescription(rs.getString("description"));
        article.setImageLink(rs.getString("imageLink"));
        article.setCreationDate(rs.getDate("creationDate"));
        article.setUserID(rs.getLong("userId"));
        return article;
    }
}
