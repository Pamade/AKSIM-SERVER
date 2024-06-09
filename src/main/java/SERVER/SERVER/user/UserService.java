package SERVER.SERVER.user;

import SERVER.SERVER.ValidationError;
import SERVER.SERVER.auth.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public Optional<User> findByEmail(String email) {
        return userDAO.findByEmail(email);
    }
    @Transactional
    public void registerUser(RegisterRequest request) throws ValidationError {
        final String email = request.getEmail();
        final String password = request.getPassword();
        final String repeatPassword = request.getRepeatPassword();
        final String regexPattern = "^(.+)@(\\S+)$";
        final int passwordLength  = 6;
        boolean isEmailCorrect = Pattern.compile(regexPattern).matcher(email).matches();

        if (email.isBlank()) {
            throw new ValidationError("Email cannot be empty", 403);
        }

        if (!isEmailCorrect) {
            throw new ValidationError("Email is not correct", 403);
        }

        if (password.length() < passwordLength) {
            throw new ValidationError("Password have to contain 6 signs", 403);
        }
//
        if (!password.equals(repeatPassword)) {
            throw new ValidationError("Passwords do not match", 400);
        }
        User user = User.builder().email(email).password(password).build();
        userDAO.addUser(user);

    }
}
