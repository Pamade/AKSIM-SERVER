package SERVER.SERVER.profile;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserProfileResponseRowMapper implements RowMapper<UserProfileResponse> {

    @Override
    public UserProfileResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setUserName(rs.getString("name"));
        userProfileResponse.setUserEmail(rs.getString("email"));
        userProfileResponse.setProfilePictureLink(rs.getString("profile_picture_link"));
        return userProfileResponse;
    }
}
