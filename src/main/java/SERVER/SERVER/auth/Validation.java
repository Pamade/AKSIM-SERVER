package SERVER.SERVER.auth;

import SERVER.SERVER.user.User;
import SERVER.SERVER.user.UserDAO;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


@Component
@AllArgsConstructor
public class Validation extends RegisterRequest{

    public Optional<List<String>> passwordValidation(String password, String repeatPassword) {
        int passwordLength = 6;
        List<String> errors = new ArrayList<>();
        if (!password.equals(repeatPassword)) {
            errors.add("Passwords do not match");
        }
        if (passwordLength > password.length()) {
            errors.add("Password must contain at least 6 characters");
        }
        return Optional.ofNullable(errors.isEmpty() ? null : errors);
    }
    public Optional<List<String>> authenticate (UserDAO userDAO) {
        List<String> errors = new ArrayList<>();
        String regexPattern = "^(.+)@(\\S+)$";
        boolean isEmailCorrect = Pattern.compile(regexPattern).matcher(getEmail()).matches() || !getEmail().isBlank();
        Optional<User> findUserByEmail = userDAO.findByEmail(getEmail());
        Optional<User> findUserByName = userDAO.findByName(getName());
        Optional<List<String>> passwordErrors = passwordValidation(getPassword(), getRepeatPassword());

        if (findUserByEmail.isPresent()) {
            errors.add("User with this email exist");
        }
        if (findUserByName.isPresent()) {
            errors.add("User with this name exist");
        }
        if (!isEmailCorrect) {
            errors.add("Email is not correct");
        }
        passwordErrors.ifPresent(errors::addAll);
        return Optional.ofNullable(errors.isEmpty() ? null : errors);
    }

}
