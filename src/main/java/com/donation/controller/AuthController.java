package com.donation.controller;

import com.donation.service.AuthService;
import com.donation.service.OtpService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final OtpService otpService;

    public AuthController(AuthService authService, OtpService otpService) {
        this.authService = authService;
        this.otpService = otpService;
    }

    @PostMapping("/signin")
    public Map<String, Object> signIn(@RequestBody Map<String, String> body,
                                      HttpServletResponse response) {
        return authService.signIn(
                body.get("email"),
                body.get("password"),
                body.get("role"),
                response);
    }

    @PostMapping("/signin/request-otp")
    public ResponseEntity<Map<String, Object>> requestOtp(
            @RequestBody Map<String, String> body) {

        String email    = body.get("email");
        String password = body.get("password");
        String role     = body.get("role");

        // ✅ Log exactly what arrives — check your Spring Boot console
        System.out.println("=== OTP REQUEST ===");
        System.out.println("Email   : " + email);
        System.out.println("Password: " + password);
        System.out.println("Role    : " + role);

        boolean valid = authService.validateCredentials(email, password, role);
        System.out.println("Valid   : " + valid);

        if (!valid) {
            // ✅ Use 400 NOT 401 — axios interceptor redirects on 401/403
            return ResponseEntity.status(400)
                    .body(Map.of("message", "Invalid email, password, or role"));
        }

        otpService.generateAndSend(email);
        return ResponseEntity.ok(Map.of("message", "OTP sent to " + email));
    }

    @PostMapping("/signin/verify-otp")
    public ResponseEntity<Map<String, Object>> verifyOtp(
            @RequestBody Map<String, String> body,
            HttpServletResponse response) {

        String email = body.get("email");
        String otp   = body.get("otp");
        String role  = body.get("role");

        if (!otpService.verify(email, otp)) {
            // ✅ Use 400 NOT 401
            return ResponseEntity.status(400)
                    .body(Map.of("message", "Invalid or expired OTP"));
        }

        Map<String, Object> result = authService.signInAfterOtp(email, role, response);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signout")
    public Map<String, Object> signOut(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return Map.of("message", "Signed out successfully");
    }
}