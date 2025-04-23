package com.SpringReactCollective.The.Online.Outfitters.service;

import com.SpringReactCollective.The.Online.Outfitters.request.LoginRequest;
import com.SpringReactCollective.The.Online.Outfitters.response.AuthResponse;
import com.SpringReactCollective.The.Online.Outfitters.response.SignupRequest;

public interface AuthService {

    void sentLoginOtp(String email) throws Exception;

    String createUser(SignupRequest req) throws Exception;

    AuthResponse signing(LoginRequest req);

}
