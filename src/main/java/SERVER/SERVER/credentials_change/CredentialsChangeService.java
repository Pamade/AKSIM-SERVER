package SERVER.SERVER.credentials_change;


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

    public void updateEmail(String oldEmail, String newEmail, String password) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        // email is a name!

        Optional<User> optionalUser = userDAO.findByEmail(oldEmail);

        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            String userPassword = user.getPassword();
            if (!passwordEncoder.matches(password, userPassword)) {
                throw new IllegalArgumentException("Invalid password.");
            }
        } else if (!currentEmail.equals(oldEmail)){
            throw new IllegalArgumentException("You can only update your own username.");
        }

        credentialsChangeDao.changeEmail(oldEmail, newEmail);

        updateSecurityContext(newEmail);
    }
    private void updateSecurityContext(String newEmail){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Authentication updatedAuth = new UsernamePasswordAuthenticationToken(
                newEmail,
                authentication.getCredentials(),
                authentication.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication((updatedAuth));
    }
}
