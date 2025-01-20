package SERVER.SERVER.credentials_change;

import SERVER.SERVER.JWTConfig.JwtService;
import SERVER.SERVER.auth.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user/")
@AllArgsConstructor
public class CredentialsChangeController {

    private final CredentialsChangeService credentialsChangeService;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    @PatchMapping("change-email")
    public ResponseEntity<CredentialsChangeResponse> changeEmail(@RequestBody ChangeEmailRequest request){

        return ResponseEntity.ok(credentialsChangeService.updateEmail(request.getOldEmail(), request.getNewEmail(), request.getPassword()));
    }
    @PatchMapping("change-name")
    public ResponseEntity<CredentialsChangeNameResponse> changeName(@RequestBody ChangeNameRequest request) {
        System.out.println("Test");
        return ResponseEntity.ok(credentialsChangeService.changeName(request.getOldName(), request.getNewName()));
    }


}
