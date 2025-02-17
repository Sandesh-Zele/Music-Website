package com.music.musicplayer.controller;
import com.music.musicplayer.model.UserDTO;
import com.music.musicplayer.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/api/registration")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getName().length() < 4) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Enter a valid Name (at least 4 characters long)."));
        } else if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Passwords do not match. Please try again."));
        } else {
            authService.saveUser(userDTO);
            return ResponseEntity.ok(new ResponseMessage("Registration successful!", true));
        }
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        boolean isValid = authService.validateUser(userDTO.getEmail(), userDTO.getPassword());
        if (isValid) {
            return ResponseEntity.ok(new ResponseMessage("Login successful!", true));
        } else {
            return ResponseEntity.badRequest().body(new ResponseMessage("Invalid email or password."));
        }
    }
    @GetMapping("/")
    public String signup() {
        return "signup";
    }

    @GetMapping("/login.html")
    public String login() {
        return "login";
    }
    @GetMapping("/index.html")
    public String index(){
        return "index";
    }
    @GetMapping("/signup.html")
    public String sign(){
        return "signup";
    }
    @GetMapping("/arjit.html")
    public String arjit(){
        return "arjit";
    }

    public static class ResponseMessage {
        private String message;
        private boolean success;

        public ResponseMessage(String message) {
            this.message = message;
            this.success = false;
        }

        public ResponseMessage(String message, boolean success) {
            this.message = message;
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public boolean isSuccess() {
            return success;
        }
    }

}

