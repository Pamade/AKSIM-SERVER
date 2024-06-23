package SERVER.SERVER.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @PostMapping("forgotPassword")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(@RequestBody  ForgotPasswordRequest request){
        return ResponseEntity.ok(authenticationService.forgotPassword(request));
    }
    @PostMapping("validateToken")
    public ResponseEntity<ValidateTokenResponse> validateToken(@RequestBody TokenRequest request) {
        return ResponseEntity.ok(authenticationService.isTokenValid(request));
    }

    @PostMapping("resetPassword")
    public ResponseEntity<ForgotPasswordResponse> resetPassword(@RequestBody ResetPasswordRequest request){
        return ResponseEntity.ok(authenticationService.resetPassword(request));
    }

}
