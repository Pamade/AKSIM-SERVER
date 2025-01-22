package SERVER.SERVER.profile;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfileService {
    private ProfileDao profileDao;

    public UserProfileResponse getUserProfile(String name){
        try {
            Optional<UserProfileResponse> profileResponseOptional = profileDao.findByNameNotLoggedUser(name);
            if (profileResponseOptional.isPresent()) {
                UserProfileResponse userProfileResponse = profileResponseOptional.get();
                String profilePictureLink = userProfileResponse.getProfilePictureLink();
                String userName = userProfileResponse.getUserName();
                String email = userProfileResponse.getUserName();
                return UserProfileResponse.builder().userEmail(email).profilePictureLink(profilePictureLink).userName(userName).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return UserProfileResponse.builder().userEmail(null).profilePictureLink(null).userName(null).build();
        }
        return UserProfileResponse.builder().userEmail(null).profilePictureLink(null).userName(null).build();
    }
}
