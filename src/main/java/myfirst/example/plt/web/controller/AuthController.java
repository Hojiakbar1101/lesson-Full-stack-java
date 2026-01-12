package myfirst.example.plt.web.controller;

import myfirst.example.plt.service.UserService;
import myfirst.example.plt.web.dto.AuthResponse;
import myfirst.example.plt.web.dto.LoginRequest;
import myfirst.example.plt.web.dto.RegsiterRequest;
import org.aspectj.weaver.patterns.IToken;
import org.aspectj.weaver.patterns.ITokenSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegsiterRequest request){
        try{
            userService.register(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(("UserRegistratsiyadan muvaffaqiyatli o'tdi"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        try{
            String token = userService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new AuthResponse(token,"login muvaffaqiyatli"));
        }catch (Exception e){
            return ResponseEntity.status(401).body("Login yoki parol xato");
        }
    }
}
