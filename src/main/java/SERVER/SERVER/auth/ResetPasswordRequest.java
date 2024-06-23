package SERVER.SERVER.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResetPasswordRequest {
    @JsonProperty("password")
    private String password;
    @JsonProperty("repeatPassword")
    private String repeatPassword;
    @JsonProperty("tokenValue")
    private String tokenValue;
    @JsonProperty("tokenType")
    private String tokenType;
}
