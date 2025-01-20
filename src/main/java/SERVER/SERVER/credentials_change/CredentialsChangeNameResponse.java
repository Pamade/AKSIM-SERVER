package SERVER.SERVER.credentials_change;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CredentialsChangeNameResponse {
    @JsonProperty("errors")
    List<String> errors;
    @JsonProperty("success_message")
    String successMessage;
}
