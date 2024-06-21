package SERVER.SERVER.auth;

import SERVER.SERVER.utils.RandomStringGenerator;
import SERVER.SERVER.email.EmailServiceImpl;
import SERVER.SERVER.JWTConfig.JwtService;
import SERVER.SERVER.user.Role;
import SERVER.SERVER.user.User;
import SERVER.SERVER.user.UserDAO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final EmailServiceImpl emailService;
    private final UserDAO userDAO;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Validation validation;

    public AuthenticationResponse register(RegisterRequest request) {
        validation.setEmail(request.getEmail());
        validation.setPassword(request.getPassword());
        validation.setRepeatPassword(request.getRepeatPassword());

        Optional<List<String>> validationErrors = validation.authenticate(userDAO);

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


    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request){
        List<String> errors = new ArrayList<>();
        try {
            User user = userDAO.findByEmail(request.getEmail()).orElseThrow();
            final int minutesToExpire = 5;
            LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(minutesToExpire);
            String tokenValue = RandomStringGenerator.generateRandomString(10);

            Token token = Token.builder().value(tokenValue).type("forgot_password").expiry_date(expiryDate).userId(user.getId()).build();
            String link = "http://localhost:5173/resetPassword/" + tokenValue;
            emailService.sendSimpleMessage(user.getEmail(), "Forgot password", "Here is a link to reset your password: " + link );
            userDAO.assignTokenForUser(token);
        } catch (NoSuchElementException e){
            errors.add("User not found");
        } catch (RuntimeException e) {
            errors.add("An error occured");
        }
        if (errors.isEmpty()) {
            return ForgotPasswordResponse.builder().successMessage("Link for reseting password sent. Check your email").build();
        }
        return ForgotPasswordResponse.builder().errors(errors).build();
    }

    public ValidateTokenResponse isTokenValid(TokenRequest request) {
        Optional<Token> tokenOptional = userDAO.findTokenByValue(request);
        System.out.println(request);

        System.out.println(tokenOptional);
        if (tokenOptional.isPresent()) {
            Token token = tokenOptional.get();
            LocalDateTime tokenDate = token.getExpiry_date().toLocalDate().atStartOfDay();
            boolean isDateBeforeExpiration = LocalDateTime.now().isAfter(tokenDate);
            System.out.println(request.getType() + token.getType() + isDateBeforeExpiration);
            if (isDateBeforeExpiration && request.getType().equals(token.getType())) {
                return ValidateTokenResponse.builder().isTokenValid(true).build();
            }
        }
        return ValidateTokenResponse.builder().isTokenValid(false).build();
    }

    //get
//    wyslac geta przy wejsciu na strone z formem
//    reset on post -> get token value where and its userid
//    public ValidateTokenForResetingPassword() {}
//    public ForgotPasswordResponse resetPassword(ResetPasswordRequest request) {
//        List<String> errors = new ArrayList<>();
//
//        try {
////            i need to take value == but the value is taken from front
//            //grab token, check if expiry date is > curr date
//        } catch () {
//
//        }
//
//    }

}
