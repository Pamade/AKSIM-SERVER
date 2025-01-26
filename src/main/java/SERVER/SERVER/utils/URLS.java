package SERVER.SERVER.utils;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class URLS {

    @Getter
    private static final String CLIENT_URL = System.getenv("CLIENT_URL");
    @Getter
    private static final String SERVER_URL = System.getenv("SERVRE_URL");

}
