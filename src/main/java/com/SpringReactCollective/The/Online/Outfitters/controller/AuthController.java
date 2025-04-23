package com.SpringReactCollective.The.Online.Outfitters.controller;

import com.SpringReactCollective.The.Online.Outfitters.Repository.UserRepository;
import com.SpringReactCollective.The.Online.Outfitters.domain.USER_ROLE;
import com.SpringReactCollective.The.Online.Outfitters.model.VerificationCode;
import com.SpringReactCollective.The.Online.Outfitters.request.LoginRequest;
import com.SpringReactCollective.The.Online.Outfitters.response.ApiResponse;
import com.SpringReactCollective.The.Online.Outfitters.response.AuthResponse;
import com.SpringReactCollective.The.Online.Outfitters.response.SignupRequest;
import com.SpringReactCollective.The.Online.Outfitters.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest request) throws Exception {

        String jwt = authService.createUser(request);

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setMessage("registeration successfull.");
        res.setRole(USER_ROLE.ROLE_CUSTOMER);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/sent/login-signup-otp")
    public ResponseEntity<ApiResponse> sendOtpHandler(@RequestBody VerificationCode request) throws Exception {

        authService.sentLoginOtp(request.getEmail());

        ApiResponse res = new ApiResponse();
        res.setMessage("otp was sent successfully.");

        return ResponseEntity.ok(res);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest request) throws Exception {

        AuthResponse authResponse = authService.signing(request);

        return ResponseEntity.ok(authResponse);
    }

}
