package SERVER.SERVER.credentials_change;

import SERVER.SERVER.JWTConfig.JwtService;
import SERVER.SERVER.auth.AuthenticationRequest;
import SERVER.SERVER.auth.AuthenticationResponse;
import SERVER.SERVER.auth.AuthenticationService;
import SERVER.SERVER.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/user/")
@AllArgsConstructor
public class CredentialsChangeController {

    private final CredentialsChangeService credentialsChangeService;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    @PostMapping("change-email")
    public ResponseEntity<String> changeEmail(@RequestBody ChangeEmailRequest request){
        String jwtToken = credentialsChangeService.updateEmail(request.getOldEmail(), request.getNewEmail(), request.getPassword());


//        UserDetails currentUser = (UserDetails) authentication.getPrincipal();
//        User user = userDao.findByEmail
//        currentUser.setEmail(request.getNewEmail());
//
//        var newToken = jwtService.generateToken(currentUser);
//        return ResponseEntity.ok(authenticationService.authenticate(request));
        return ResponseEntity.ok(jwtToken);
//        return ResponseEntity.ok(authenticationService.authenticate(request));

//        return ResponseEntity.ok(jwtToken);
    }
}
