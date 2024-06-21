package SERVER.SERVER.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ForgotPasswordResponse {
    @JsonProperty("errors")
    private List<String> errors;
    @JsonProperty("successMessage")
    private String successMessage;

}
