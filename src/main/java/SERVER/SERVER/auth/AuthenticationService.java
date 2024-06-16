package SERVER.SERVER.auth;

import SERVER.SERVER.JWTConfig.JwtService;
import SERVER.SERVER.user.Role;
import SERVER.SERVER.user.User;
import SERVER.SERVER.user.UserDAO;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {

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
}
