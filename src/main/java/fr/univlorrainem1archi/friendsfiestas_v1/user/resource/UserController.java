package fr.univlorrainem1archi.friendsfiestas_v1.user.resource;

import fr.univlorrainem1archi.friendsfiestas_v1.common.Response;
import fr.univlorrainem1archi.friendsfiestas_v1.common.ResponseBuilder;
import fr.univlorrainem1archi.friendsfiestas_v1.security.exception.EmailExistException;
import fr.univlorrainem1archi.friendsfiestas_v1.security.exception.UsernameExistException;
import fr.univlorrainem1archi.friendsfiestas_v1.security.jwt.JwtTokenProvider;
import fr.univlorrainem1archi.friendsfiestas_v1.user.models.User;
import fr.univlorrainem1archi.friendsfiestas_v1.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/friends-fiestas/users/")
public class UserController {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(JwtTokenProvider jwtTokenProvider, UserService userService, AuthenticationManager authenticationManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/")
    public ResponseEntity<Response> getUsers(){
        return new ResponseBuilder(HttpStatus.OK,"Retrieving all users","users",userService.getUsers()).buildResponse();
    }

    @PostMapping("/register")
    public ResponseEntity<Response> registration(@RequestBody User user) throws EmailExistException, UsernameExistException {
        return new ResponseBuilder(HttpStatus.CREATED,"User registrated successfully!","user",userService.register(user)).buildResponse();
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody User user){
        return new ResponseBuilder(HttpStatus.OK,"User logged successfully!","user",userService.login(user)).buildResponse();
    }

}
