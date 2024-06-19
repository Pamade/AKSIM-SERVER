package SERVER.SERVER.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TokenRequest {
    @JsonProperty
    private String value;
    @JsonProperty
    private String type;
}
