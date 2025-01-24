package SERVER.SERVER.profile;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ProfileDao {
    private JdbcTemplate jdbcTemplate;
    public void updateUserProfilePicture(String userEmail, String filename) {
        String sql = "UPDATE users SET profile_picture_link = ? WHERE email = ?";

        try {
            jdbcTemplate.update(sql, filename, userEmail);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<UserProfileResponse> findByNameNotLoggedUser(String name) {
        String sql = "SELECT name, email, profile_picture_link FROM users WHERE name = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{name}, new UserProfileResponseRowMapper()));
        } catch (Exception e) {
            return Optional.empty(); // return null if user is not found
        }
    }


    public List<UserProfileResponse> findUsers(){
        String sql = "SELECT name, email, profile_picture_link FROM users";
        try {
            return jdbcTemplate.query(sql, new UserProfileResponseRowMapper());
        }
        catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public List<UserProfileResponse> findUsers(int limit){
        String sql = "SELECT name, email, profile_picture_link FROM users LIMIT ?";
        try {
            return jdbcTemplate.query(sql, new Object[]{limit}, new UserProfileResponseRowMapper());
        }
        catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
