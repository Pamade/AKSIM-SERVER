package SERVER.SERVER.auth;

import SERVER.SERVER.user.User;
import SERVER.SERVER.user.UserDAO;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


@Component
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterValidation extends RegisterRequest{

    private final String regexPattern = "^(.+)@(\\S+)$";
    private final int passwordLength  = 6;

    @Setter
    private List<String> errors = new ArrayList<>();

    public Optional<List<String>> validationErrors (UserDAO userDAO) {
        errors.clear();

        boolean isEmailCorrect = Pattern.compile(regexPattern).matcher(getEmail()).matches();

        Optional<User> findUserByEmail = userDAO.findByEmail(getEmail());

        if (findUserByEmail.isPresent()) {
            errors.add("User with this email exist");
        }

        if (!getPassword().equals(getRepeatPassword())) {
            errors.add("Passwords do not match");
        }

        if (getEmail().isBlank()) {
            errors.add("Email can't be empty");
        }

        if (passwordLength > getPassword().length()) {
            errors.add("Password must contain at least 6 characters");
        }

        if (!isEmailCorrect) {
            errors.add("Email is not correct");
        }
        return Optional.ofNullable(errors.isEmpty() ? null : errors);
    }

}
