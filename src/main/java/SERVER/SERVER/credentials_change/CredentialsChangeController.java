package SERVER.SERVER.credentials_change;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/")
public class CredentialsChangeController {

    CredentialsChangeService credentialsChangeService;
    @PostMapping("change-email")
    public ResponseEntity<String> changeEmail(@RequestBody ChangeEmailRequest request){
        credentialsChangeService.updateEmail(request.getOldEmail(), request.getNewEmail(), request.getPassword());
        return ResponseEntity.ok("Changed");
    }
}
