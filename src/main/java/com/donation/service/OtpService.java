package com.donation.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    private final JavaMailSender mailSender;
    private final Map<String, String> otpStore = new ConcurrentHashMap<>();
    public OtpService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void generateAndSend(String email) {
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        otpStore.put(email, otp);

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Your Sign-In OTP");
        msg.setText("Your OTP is: " + otp + "\nValid for 5 minutes. Do not share it.");
        mailSender.send(msg);

        // Auto-expire after 5 minutes
        new Thread(() -> {
            try { Thread.sleep(300_000); } catch (InterruptedException ignored) {}
            otpStore.remove(email);
        }).start();
    }

    public boolean verify(String email, String otp) {
        String stored = otpStore.get(email);
        if (stored != null && stored.equals(otp)) {
            otpStore.remove(email); // one-time use
            return true;
        }
        return false;
    }
}