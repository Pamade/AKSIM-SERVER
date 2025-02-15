package SERVER.SERVER.credentials_change;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsChangeResponse {
    @JsonProperty("errors")
    private List<String> errors;
    @JsonProperty("access_token")
    private String access_token;

}
