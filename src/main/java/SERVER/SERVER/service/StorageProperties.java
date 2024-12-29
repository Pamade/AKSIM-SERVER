package SERVER.SERVER.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


// Ensures it's recognized as a Spring bean
@ConfigurationProperties(prefix = "storage")
@Getter
@Setter
@Component
public class StorageProperties {


    private String location = "uploads";

}
