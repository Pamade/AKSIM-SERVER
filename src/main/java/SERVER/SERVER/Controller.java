package SERVER.SERVER;

import SERVER.SERVER.user.UserRegister;
import SERVER.SERVER.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controller {
    
    @Autowired
    private UserService userService;
    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody UserRegister userRegister){
        try {
            userService.registerUser(userRegister);
            return new ResponseEntity<>("User Created", HttpStatus.CREATED);
        } catch (ValidationError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }
}
