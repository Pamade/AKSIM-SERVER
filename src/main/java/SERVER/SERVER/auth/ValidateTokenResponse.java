package SERVER.SERVER.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidateTokenResponse {
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("error")
    private String message;
}
