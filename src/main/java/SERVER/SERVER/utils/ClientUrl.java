package SERVER.SERVER.utils;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class ClientUrl {
    @Getter
    private static final String CLIENT_URL = System.getenv("CLIENT_URL");

}
