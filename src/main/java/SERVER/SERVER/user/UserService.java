package SERVER.SERVER.user;

import SERVER.SERVER.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;
    @Transactional
    public void registerUser(UserRegister userRegister) throws ValidationError {
        final String email = userRegister.getEmail();
        final String password = userRegister.getPassword();
        final String repeatPassword = userRegister.getRepeatPassword();
        final String regexPattern = "^(.+)@(\\S+)$";
        final int passwordLength  = 6;
        boolean isEmailCorrect = Pattern.compile(regexPattern).matcher(email).matches();

        if (email.isBlank()) {
            throw new ValidationError("Email cannot be empty", 400);
        }

        if (!isEmailCorrect) {
            throw new ValidationError("Email is not correct", 400);
        }

        if (password.length() < passwordLength) {
            throw new ValidationError("Password have to contain 6 signs", 400);
        }

        if (!password.equals(repeatPassword)) {
            throw new ValidationError("Passwords do not match", 400);
        }




        userDAO.addUser(userRegister);

    }
}
