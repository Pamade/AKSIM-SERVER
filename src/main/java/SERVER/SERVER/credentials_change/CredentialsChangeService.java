package SERVER.SERVER.credentials_change;


import SERVER.SERVER.JWTConfig.JwtService;
import SERVER.SERVER.auth.Validation;
import SERVER.SERVER.user.User;
import SERVER.SERVER.user.UserDAO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CredentialsChangeService {
    private final CredentialsChangeDao credentialsChangeDao;
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final Validation validation;
    public CredentialsChangeResponse updateEmail(String oldEmail, String newEmail, String password) {
        List<String> errors = new ArrayList<>();

        try {
            Optional<User> optionalUser = userDAO.findByEmail(oldEmail);
            Optional<User> optionalUserInDb = userDAO.findByEmail(newEmail);
            if (optionalUserInDb.isPresent()) {
                errors.add("User with that email exists");
                return CredentialsChangeResponse.builder().errors(errors).build();
            }

            if (optionalUser.isPresent()){
                User user = optionalUser.get();
                String userPassword = user.getPassword();
                if (!passwordEncoder.matches(password, userPassword)) {
                    errors.add("Wrong password");
                    return CredentialsChangeResponse.builder().errors(errors).build();
                }
                credentialsChangeDao.changeEmail(oldEmail, newEmail);

                user.setEmail(newEmail);
                updateSecurityContext(user);
                var token = jwtService.generateToken(user);

                return CredentialsChangeResponse.builder().access_token(token).build();
            }
        }
        catch (BadCredentialsException e) {
            errors.add("Bad credentials");
        }
        catch (Exception e) {
            errors.add("Server error");
        }

        return CredentialsChangeResponse.builder().errors(errors).build();
    }
    public CredentialsChangeNameResponse changeName(String oldName, String newName){
        List<String> errors = new ArrayList<>();

        if (newName.length() < 6) {
            errors.add("Name must be longer");
        }
        else {
            try {
                Optional<User> optionalUserWithNewName = userDAO.findByName(newName);
                Optional<User> optionalUser = userDAO.findByName(oldName);

                if (optionalUserWithNewName.isPresent()) {
                    errors.add("User with that name exists");
                }
                else if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    userDAO.changeUserName(newName, user.getEmail());
                    return CredentialsChangeNameResponse.builder().successMessage("Name changed").build();
                } else {
                    errors.add("User not found");
                }

            } catch (Exception e) {
                e.printStackTrace();
                errors.add("Server error");
            }
        }
        return CredentialsChangeNameResponse.builder().errors(errors).build();
    }

    public CredentialsChangeResponse changePassword(String userEmail, String validationPassword, String newPassword) {
        List<String> errors = new ArrayList<>();

        try {
            Optional<User> optionalUser = userDAO.findByEmail(userEmail);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                String userPassword = user.getPassword();
                if (!passwordEncoder.matches(validationPassword, userPassword)) {
                    errors.add("Passwords do not match");
                }
                else if ( newPassword.length() < 6 || validationPassword.length() < 6) {
                    errors.add("Password must contain at least 6 characters");
                }
                else {
                    String hashedPassword = userDAO.hashPassword(newPassword);
                    credentialsChangeDao.changePassword(userEmail, hashedPassword);
                    user.setPassword(hashedPassword);
                    updateSecurityContext(user);
                    var token = jwtService.generateToken(user);
                    return CredentialsChangeResponse.builder().access_token(token).build();
                }
            } else {
                errors.add("User not found");
            }
        }
        catch (IllegalArgumentException e) {
            errors.add("Fill all inputs");
        }
        catch (Exception e) {
            errors.add("Server error");
            e.printStackTrace();
        }
        return CredentialsChangeResponse.builder().errors(errors).build();
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
