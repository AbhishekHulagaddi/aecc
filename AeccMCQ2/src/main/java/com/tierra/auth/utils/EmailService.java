package com.tierra.auth.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendOtpForForgotPassword(String toEmail) {
        String otp = generateOtp();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Tierra: Agnivesha Entrance Coaching Classes - OTP for Forgot Password Reset");

        String body = "Dear User,\n\n"
                    + "You have requested to reset your password. Please use the following One-Time Password (OTP) to proceed:\n\n"
                    + "OTP: " + otp + "\n\n"
                    + "⚠️ This OTP is valid for 10 minutes only.\n"
                    + "If you did not request this, please ignore this email.\n\n"
                    + "Note: This is an automated email. Please do not reply to this message.\n\n"
                    + "Regards,\n"
                    + "Tierra: Agnivesha Entrance Coaching Classes Support Team";

        message.setText(body);
        message.setFrom("tierra.agniveshacoaching01@gmail.com");
        mailSender.send(message);
        return otp;
    }
    
    public String sendOtpForNewUser(String toEmail) {
        String otp = generateOtp();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Tierra: Agnivesha Entrance Coaching Classes - OTP for Registration");

        String body = "Dear User,\n\n"
                    + "You have requested to Register as New User. Please use the following One-Time Password (OTP) to proceed:\n\n"
                    + "OTP: " + otp +"\n" 
                    + "Thank You for Registering.\n\n"
                    + "⚠️ This OTP is valid for 10 minutes only.\n"
                    + "If you did not request this, please ignore this email.\n\n"
                    + "Note: This is an automated email. Please do not reply to this message.\n\n"
                    + "Regards,\n"
                    + "Tierra: Agnivesha Entrance Coaching Classes Support Team";

        message.setText(body);
        message.setFrom("tierra.agniveshacoaching01@gmail.com");
        mailSender.send(message);
        return otp;
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otp);
    }
}
