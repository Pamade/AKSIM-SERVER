package SERVER.SERVER.auth;

import SERVER.SERVER.utils.RandomStringGenerator;
import SERVER.SERVER.email.EmailServiceImpl;
import SERVER.SERVER.JWTConfig.JwtService;
import SERVER.SERVER.user.Role;
import SERVER.SERVER.user.User;
import SERVER.SERVER.user.UserDAO;
import SERVER.SERVER.utils.URLS;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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
    private final URLS urls;

    public AuthenticationResponse register(RegisterRequest request) {
        validation.setEmail(request.getEmail());
        validation.setName(request.getName());
        validation.setPassword(request.getPassword());
        validation.setRepeatPassword(request.getRepeatPassword());

        Optional<List<String>> validationErrors = validation.authenticate(userDAO);

        if (validationErrors.isPresent()) {
            return AuthenticationResponse.builder().errors(validationErrors.get()).build();
        }

        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(request.getPassword())
                .role(Role.USER).build();

        userDAO.addUser(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        ArrayList<String> errors = new ArrayList<>();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            User user = userDAO.findByEmail(request.getEmail()).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder().accessToken(jwtToken).build();
        } catch (BadCredentialsException e) {
            errors.add("Invalid credentials");
            e.printStackTrace();
        }
         catch (AuthenticationException e) {
            errors.add("Authentication Failed");
         }
        catch (Exception e) {
            errors.add("Internal server error");
        }
        return AuthenticationResponse.builder().errors(errors).build();
    }


    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request){
        List<String> errors = new ArrayList<>();
        try {
            User user = userDAO.findByEmail(request.getEmail()).orElseThrow();
            final int minutesToExpire = 5;
            LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(minutesToExpire);
            String tokenValue = RandomStringGenerator.generateRandomString(10);

            Token token = Token.builder().value(tokenValue).type("forgot_password").expiry_date(expiryDate).userId(user.getId()).build();
            String link = URLS.getCLIENT_URL() +  "/resetPassword/" + tokenValue;
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
        if (tokenOptional.isPresent()) {
            Token token = tokenOptional.get();
            LocalDateTime tokenDate = token.getExpiry_date();
            boolean isDateBeforeExpiration = LocalDateTime.now().isBefore(tokenDate);
            if (isDateBeforeExpiration && request.getType().equals(token.getType())) {
                return ValidateTokenResponse.builder().isTokenValid(true).build();
            }
        }
        return ValidateTokenResponse.builder().isTokenValid(false).build();
    }

    public ForgotPasswordResponse resetPassword(ResetPasswordRequest request) {
        List<String> errors = new ArrayList<>();
        Optional<List<String>> errorsPassword = validation.passwordValidation(request.getPassword(), request.getRepeatPassword());
        errorsPassword.ifPresent(errors::addAll);

        try {
            userDAO.resetPassword(request);
        } catch (AuthenticationException e) {
            errors.add("Authenctication problem");
        } catch (RuntimeException | SQLException e) {
            errors.add("Server error");
        }

        return errors.isEmpty() ? ForgotPasswordResponse.builder().successMessage("Password changed").build() :
                ForgotPasswordResponse.builder().errors(errors).build();

    }

}
