package SERVER.SERVER.auth;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

@Data
@Builder
public class Token {
    private long id;
    private String type;
    private String value;
    private LocalDateTime expiry_date;
    private long userId;
}
