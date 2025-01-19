package SERVER.SERVER.credentials_change;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeEmailRequest {
    private String oldEmail;
    private String newEmail;
    private String password;
}
