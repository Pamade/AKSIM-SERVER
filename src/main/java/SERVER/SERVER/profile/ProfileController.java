package SERVER.SERVER.profile;

import SERVER.SERVER.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class ProfileController {
    private StorageService storageService;
    private ProfileDao profileDao;
    private ProfileService profileService;
    @PatchMapping("user/add-profile-picture")
    public String addProfilePicture(@RequestParam("file")MultipartFile file,
                                @RequestParam("userEmail") String userEmail){
        String generatedName = storageService.renameFile(file);
        String newFilename = "http://localhost:8080/uploads/" + generatedName;
        System.out.println(file +  userEmail);
        storageService.store(file);
        profileDao.updateUserProfilePicture(userEmail, newFilename);
        return newFilename;
    }

    @GetMapping("/content/get-user-profile/{name}")
    public ResponseEntity<UserProfileResponse> getUserProfile (@PathVariable("name") String name) {
        return ResponseEntity.ok(profileService.getUserProfile(name));
    }

    @GetMapping("/content/get-users-profiles")
    public ResponseEntity<List<UserProfileResponse>> getUsersProfiles() {
        return ResponseEntity.ok(profileDao.findUsers(7));
    }

    @GetMapping("/content/get-users-aksim")
    public ResponseEntity<List<UserProfileResponse>> getUsersAksim() {
        return ResponseEntity.ok(profileDao.findUsers());
    }

}
