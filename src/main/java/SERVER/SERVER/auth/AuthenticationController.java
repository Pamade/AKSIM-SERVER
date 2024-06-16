package SERVER.SERVER.auth;

import SERVER.SERVER.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final EmailServiceImpl emailService;
    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        emailService.sendSimpleMessage("cwedal2@gmail.com", "Policja", "this huj is message");
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
//    @PostMapping
//    public ResponseEntity<> generateNewPassword(@RequestBody GenerateNewPasswordRequest request){
//
//    }

}
