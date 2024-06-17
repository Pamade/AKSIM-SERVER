package SERVER.SERVER.user;

import SERVER.SERVER.auth.Token;
import SERVER.SERVER.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;



import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public User addUser(User user){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String hashedPassword = encoder.encode(user.getPassword());
        String email = user.getEmail();
        String sql = "INSERT INTO users (email, password) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, email);
                ps.setString(2, hashedPassword);
                return ps;
            }
        }, keyHolder);
        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    public void assignTokenForUser(Token token) {
//        INSERT INTO tokens VALUES (1, 'asdasd', 'forgotPassword', CURRENT_DATE(), 1);

//        INSERT INTO tokens (value, type, expiry_date, user_id) VALUES ('asdasd', 'forgotPassword', CURRENT_DATE(), 1);

        String sql = "INSERT INTO tokens (value, type, expiry_date, user_id) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, token.getValue(), token.getType(), token.getExpiry_date(), token.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{email}, new UserRowMapper());
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            // Map other fields if necessary
            return user;
        }
    }

}
