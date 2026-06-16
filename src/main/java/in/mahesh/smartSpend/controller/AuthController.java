package in.mahesh.smartSpend.controller;

import in.mahesh.smartSpend.dto.AuthResponse;
import in.mahesh.smartSpend.dto.LoginRequest;
import in.mahesh.smartSpend.dto.RegisterRequest;
import in.mahesh.smartSpend.response.ApiResponse;
import in.mahesh.smartSpend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest registerRequest){
        AuthResponse token = authService.register(registerRequest);
        return ResponseEntity.ok(ApiResponse.success("User Register successfully",token));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest loginRequest){
        AuthResponse token = authService.login(loginRequest);

        return ResponseEntity.ok(ApiResponse.success("User Login successfully",token));
    }

}
