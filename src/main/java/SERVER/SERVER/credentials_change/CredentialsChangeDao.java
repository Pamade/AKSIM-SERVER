package SERVER.SERVER.credentials_change;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CredentialsChangeDao {
    private JdbcTemplate jdbcTemplate;

    public void changeEmail(String userOldEmail, String userNewEmail) {
        String sql = "UPDATE users SET email = ? WHERE email = ?";

        try {
            jdbcTemplate.update(sql, userNewEmail, userOldEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
