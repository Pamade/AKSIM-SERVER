package SERVER.SERVER.auth;

import SERVER.SERVER.utils.RandomStringGenerator;
import SERVER.SERVER.email.EmailServiceImpl;
import SERVER.SERVER.JWTConfig.JwtService;
import SERVER.SERVER.user.Role;
import SERVER.SERVER.user.User;
import SERVER.SERVER.user.UserDAO;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final EmailServiceImpl emailService;
    private final UserDAO userDAO;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RegisterValidation registerValidation;

    public AuthenticationResponse register(RegisterRequest request) {
        registerValidation.setEmail(request.getEmail());
        registerValidation.setPassword(request.getPassword());
        registerValidation.setRepeatPassword(request.getRepeatPassword());

        Optional<List<String>> validationErrors = registerValidation.validationErrors(userDAO);

        if (validationErrors.isPresent()) {
            return AuthenticationResponse.builder().errors(validationErrors.get()).build();
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.USER).build();

        userDAO.addUser(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userDAO.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(jwtToken).build();
    }


    public String forgotPassword(String email){
        User user = userDAO.findByEmail(email).orElseThrow();
        final int minutesToExpire = 5;
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(minutesToExpire);
        String tokenValue = RandomStringGenerator.generateRandomString(10);

        Token token = Token.builder().value(tokenValue).type("forgot_password").expiry_date(expiryDate).userId(user.getId()).build();
        String link = "http://localhost:5173/forgotPassword?token=" + tokenValue;
        emailService.sendSimpleMessage(user.getEmail(), "Forgot password", "Here is a link to reset your password: " + link );
        userDAO.assignTokenForUser(token);
        return "test";
//        change this test
                //dodaj token do bazy danych i wyslij emaila, currently
//              if expiry date > current date ? authenticate and
//        PasswordResetToken resetToken = new PasswordResetToken(token, user.getId())

    }

}
