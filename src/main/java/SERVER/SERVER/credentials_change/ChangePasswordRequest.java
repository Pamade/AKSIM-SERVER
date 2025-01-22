package SERVER.SERVER.credentials_change;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    String userEmail;
    String validationPassword;
    String newPassword;
}
