package SERVER.SERVER.credentials_change;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeNameRequest {
    private String oldName;
    private String newName;
}
