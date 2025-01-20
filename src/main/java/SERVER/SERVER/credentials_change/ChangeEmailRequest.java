package SERVER.SERVER.credentials_change;

import SERVER.SERVER.auth.AuthenticationRequest;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeEmailRequest {

    private String oldEmail;
    private String newEmail;
    private String password;
}
