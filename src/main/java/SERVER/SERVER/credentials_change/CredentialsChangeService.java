package SERVER.SERVER.credentials_change;


import SERVER.SERVER.JWTConfig.JwtService;
import SERVER.SERVER.user.User;
import SERVER.SERVER.user.UserDAO;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CredentialsChangeService {
    private final CredentialsChangeDao credentialsChangeDao;
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public String updateEmail(String oldEmail, String newEmail, String password) {
        String token = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // email is a name!

        Optional<User> optionalUser = userDAO.findByEmail(oldEmail);
        System.out.println(oldEmail + newEmail + password);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            String userPassword = user.getPassword();
            if (!passwordEncoder.matches(password, userPassword)) {
                throw new IllegalArgumentException("Invalid password.");
            }
            credentialsChangeDao.changeEmail(oldEmail, newEmail);

            user.setEmail(newEmail);
            updateSecurityContext(user);
            token = jwtService.generateToken(user);

        }
        System.out.println(token);

        return token;
    }
    private void updateSecurityContext(User updatedUser){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Authentication updatedAuth = new UsernamePasswordAuthenticationToken(
                updatedUser,
                updatedUser.getPassword(),
                updatedUser.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication((updatedAuth));
    }
}
