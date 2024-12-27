package SERVER.SERVER.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
public class ArticleResponse {
    @JsonProperty("success_message")
    String successMessage;
    @JsonProperty("errors")
    List<String> errors;
}
