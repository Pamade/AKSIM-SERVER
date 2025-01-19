package SERVER.SERVER.user;

import SERVER.SERVER.auth.ResetPasswordRequest;
import SERVER.SERVER.auth.Token;
import SERVER.SERVER.auth.TokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;



import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


@Repository
public class UserDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    String hashPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        return encoder.encode(password);
    };
    public void addUser(User user){
        String hashedPassword = hashPassword(user.getPassword());
        String sql = "INSERT INTO users (email, name, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getName(), hashedPassword);
    }
    public void assignTokenForUser(Token token) {
        String sql = "INSERT INTO tokens (value, type, expiry_date, user_id) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, token.getValue(), token.getType(), token.getExpiry_date(), token.getUserId());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public Optional<Token> findTokenByValue(TokenRequest request) {
        String sql = "SELECT * FROM tokens WHERE value = ?";
        try {
            Token tokenFind = jdbcTemplate.queryForObject(sql, new TokenRowMapper(), request.getValue());
            return Optional.ofNullable(tokenFind);
        } catch(Exception e) {
            return Optional.empty();
        }
    }

    public static class TokenRowMapper implements RowMapper<Token> {
        @Override
        public Token mapRow(ResultSet rs, int rowNum) throws SQLException {
            Token token = new Token();
            token.setId(rs.getLong("id"));
            token.setUserId(rs.getLong("user_id"));
            token.setType(rs.getString("type"));
            token.setValue(rs.getString("value"));
            token.setExpiry_date(rs.getTimestamp("expiry_date").toLocalDateTime());

            return token;
        };


    };

    public void resetPassword(ResetPasswordRequest resetPasswordRequest) throws SQLException{
        String sql = "UPDATE users u JOIN tokens t ON u.id = t.user_id SET u.password = ? WHERE t.value = ? AND t.type = ?";
        String hashedPassword = hashPassword(resetPasswordRequest.getPassword());
        String tokenValue = resetPasswordRequest.getTokenValue();
        String tokenType = resetPasswordRequest.getTokenType();
        try {
            jdbcTemplate.update(sql, hashedPassword, tokenValue, tokenType );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{email}, new UserRowMapper()));
        } catch (Exception e) {
            return Optional.empty(); // return null if user is not found
        }
    }

    public Optional<User> findByName(String name) {
        String sql = "SELECT * FROM users WHERE name = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{name}, new UserRowMapper()));
        } catch (Exception e) {
            return Optional.empty(); // return null if user is not found
        }
    }


    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));

            return user;
        }
    }

}
