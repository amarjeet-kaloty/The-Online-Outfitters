package com.SpringReactCollective.The.Online.Outfitters.service.impl;

import com.SpringReactCollective.The.Online.Outfitters.Repository.CartRepository;
import com.SpringReactCollective.The.Online.Outfitters.Repository.UserRepository;
import com.SpringReactCollective.The.Online.Outfitters.Repository.VerificationCodeRepository;
import com.SpringReactCollective.The.Online.Outfitters.config.JwtProvider;
import com.SpringReactCollective.The.Online.Outfitters.domain.USER_ROLE;
import com.SpringReactCollective.The.Online.Outfitters.model.Cart;
import com.SpringReactCollective.The.Online.Outfitters.model.User;
import com.SpringReactCollective.The.Online.Outfitters.model.VerificationCode;
import com.SpringReactCollective.The.Online.Outfitters.request.LoginRequest;
import com.SpringReactCollective.The.Online.Outfitters.response.AuthResponse;
import com.SpringReactCollective.The.Online.Outfitters.response.SignupRequest;
import com.SpringReactCollective.The.Online.Outfitters.service.AuthService;
import com.SpringReactCollective.The.Online.Outfitters.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final CustomeUserServiceImpl customeUserService;
    private final EmailService emailService;
    private final JwtProvider jwtProvider;

    @Override
    public void sentLoginOtp(String email) throws Exception {
        String SIGNING_PREFIX = "signin_";

        if (email.startsWith(SIGNING_PREFIX)) {
            email = email.substring(SIGNING_PREFIX.length());

            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new Exception("User does not exist.");
            }
        }

        VerificationCode exist = verificationCodeRepository.findByEmail(email);
        if (exist != null) {
            verificationCodeRepository.delete(exist);
        }

        String otp = OtpUtil.generateOtop();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);
        verificationCodeRepository.save(verificationCode);

        String subject = "The-Online-Outfitters login/signup OTP";
        String text = "Your login/signup otp is: " + otp;

        emailService.sendVerificationOtpEmail(email, otp, subject, text);
    }

    @Override
    public String createUser(SignupRequest req) throws Exception {

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(req.getEmail());

        if (verificationCode == null || !verificationCode.getOtp().equals(req.getOtp())) {
            throw new Exception("Incorrect OTP!");
        }

        User user = userRepository.findByEmail(req.getEmail());
        if (user == null) {
           User newUser = new User();
           newUser.setEmail(req.getEmail());
           newUser.setFullName(req.getFullName());
           newUser.setRole(USER_ROLE.ROLE_CUSTOMER);
           newUser.setMobile("407-457-1234");
           newUser.setPassword(passwordEncoder.encode(req.getOtp()));

           user = userRepository.save(newUser);

           Cart cart = new Cart();
           cart.setUser(user);
           cartRepository.save(cart);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);
    }

    @Override
    public AuthResponse signing(LoginRequest req) {
       String username = req.getEmail();
       String otp = req.getOtp();

       Authentication authentication = authenticate(username, otp);
       SecurityContextHolder.getContext().setAuthentication(authentication);

       String token = jwtProvider.generateToken(authentication);

       AuthResponse authResponse = new AuthResponse();
       authResponse.setJwt(token);
       authResponse.setMessage("Login success!");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
        authResponse.setRole(USER_ROLE.valueOf(roleName));

        return authResponse;
    }

    private Authentication authenticate(String username, String otp) {
        UserDetails userDetails = customeUserService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(username);

        if (verificationCode == null || !verificationCode.getOtp().equals(otp)) {
            throw new BadCredentialsException("Invalid otp!");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
