package in.mahesh.smartSpend.service;

import in.mahesh.smartSpend.dto.AuthResponse;
import in.mahesh.smartSpend.dto.LoginRequest;
import in.mahesh.smartSpend.dto.RegisterRequest;
import in.mahesh.smartSpend.entity.User;
import in.mahesh.smartSpend.repository.UserRepository;
import in.mahesh.smartSpend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest registerRequest){
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email already exist");
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
        return new AuthResponse();
    }

    public AuthResponse login(LoginRequest loginRequest){
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Invalid email"));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new BadCredentialsException("Invalid Credential");
        }
        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token, user.getUsername(),user.getEmail());
    }
}
