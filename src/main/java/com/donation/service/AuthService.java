package com.donation.service;

import com.donation.entity.Consumer;
import com.donation.entity.Donor;
import com.donation.repository.ConsumerRepository;
import com.donation.repository.DonorRepository;
import com.donation.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final DonorRepository donorRepository;
    private final ConsumerRepository consumerRepository;
    private final JwtUtil jwtUtil;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    public AuthService(DonorRepository donorRepository,
                       ConsumerRepository consumerRepository,
                       JwtUtil jwtUtil) {
        this.donorRepository = donorRepository;
        this.consumerRepository = consumerRepository;
        this.jwtUtil = jwtUtil;
    }

    // ── Existing: direct sign-in (unchanged) ─────────────────────────────────
    public Map<String, Object> signIn(String email, String password,
                                      String role, HttpServletResponse response) {
        String normalizedRole = role.toLowerCase();
        String token;
        Map<String, Object> result = new HashMap<>();

        if (normalizedRole.equals("donor")) {
            Donor donor = donorRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Invalid email or password"));
            if (!donor.getPassword().equals(password))
                throw new RuntimeException("Invalid email or password");

            token = jwtUtil.generateToken(email, "DONOR", donor.getId());
            result.put("userId", donor.getId());
            result.put("name",   donor.getName());
            result.put("email",  donor.getEmail());
            result.put("role",   "DONOR");

        } else if (normalizedRole.equals("consumer")) {
            Consumer consumer = consumerRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Invalid email or password"));
            if (!consumer.getPassword().equals(password))
                throw new RuntimeException("Invalid email or password");

            token = jwtUtil.generateToken(email, "CONSUMER", consumer.getId());
            result.put("userId", consumer.getId());
            result.put("name",   consumer.getName());
            result.put("email",  consumer.getEmail());
            result.put("role",   "CONSUMER");

        } else if (normalizedRole.equals("admin")) {
            if (!email.equals(adminEmail) || !password.equals(adminPassword))
                throw new RuntimeException("Invalid admin credentials");

            token = jwtUtil.generateToken(email, "ADMIN", 0L);
            result.put("userId", 0L);
            result.put("name",   "Admin");
            result.put("email",  email);
            result.put("role",   "ADMIN");

        } else {
            throw new RuntimeException("Invalid role: " + role);
        }

        addJwtCookie(response, token);
        result.put("token", token);
        return result;
    }

    // ── New: Step 1 — check credentials only, no JWT issued ──────────────────
 // ── New: Step 1 — check credentials only, no JWT issued ──────────────────
    public boolean validateCredentials(String email, String password, String role) {
        String normalizedRole = role != null ? role.toLowerCase().trim() : "";
        
        System.out.println("=== VALIDATE CREDENTIALS ===");
        System.out.println("Role normalized: '" + normalizedRole + "'");

        try {
            if (normalizedRole.equals("donor")) {
                var donorOpt = donorRepository.findByEmail(email);
                System.out.println("Donor found: " + donorOpt.isPresent());
                if (donorOpt.isEmpty()) return false;
                
                String stored = donorOpt.get().getPassword();
                System.out.println("Stored password: '" + stored + "'");
                System.out.println("Given password : '" + password + "'");
                System.out.println("Match: " + stored.equals(password));
                return stored.equals(password);

            } else if (normalizedRole.equals("consumer")) {
                var consumerOpt = consumerRepository.findByEmail(email);
                System.out.println("Consumer found: " + consumerOpt.isPresent());
                if (consumerOpt.isEmpty()) return false;

                String stored = consumerOpt.get().getPassword();
                System.out.println("Stored password: '" + stored + "'");
                System.out.println("Given password : '" + password + "'");
                System.out.println("Match: " + stored.equals(password));
                return stored.equals(password);

            } else if (normalizedRole.equals("admin")) {
                System.out.println("Admin email match: " + email.equals(adminEmail));
                System.out.println("Admin pass match : " + password.equals(adminPassword));
                return email.equals(adminEmail) && password.equals(adminPassword);
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
        }

        System.out.println("No role matched!");
        return false;
    }

    // ── New: Step 2 — credentials already verified, just issue JWT ───────────
    public Map<String, Object> signInAfterOtp(String email, String role,
                                               HttpServletResponse response) {
        String normalizedRole = role.toLowerCase();
        String token;
        Map<String, Object> result = new HashMap<>();

        if (normalizedRole.equals("donor")) {
            Donor donor = donorRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            token = jwtUtil.generateToken(email, "DONOR", donor.getId());
            result.put("userId", donor.getId());
            result.put("name",   donor.getName());
            result.put("email",  donor.getEmail());
            result.put("role",   "DONOR");

        } else if (normalizedRole.equals("consumer")) {
            Consumer consumer = consumerRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            token = jwtUtil.generateToken(email, "CONSUMER", consumer.getId());
            result.put("userId", consumer.getId());
            result.put("name",   consumer.getName());
            result.put("email",  consumer.getEmail());
            result.put("role",   "CONSUMER");

        } else if (normalizedRole.equals("admin")) {
            token = jwtUtil.generateToken(email, "ADMIN", 0L);
            result.put("userId", 0L);
            result.put("name",   "Admin");
            result.put("email",  email);
            result.put("role",   "ADMIN");

        } else {
            throw new RuntimeException("Invalid role: " + role);
        }

        addJwtCookie(response, token);
        result.put("token", token);
        return result;
    }

    // ── Private helper: cookie logic in one place ─────────────────────────────
    private void addJwtCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400);
        response.addCookie(cookie);
    }
}