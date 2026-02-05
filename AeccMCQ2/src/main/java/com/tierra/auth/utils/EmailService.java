package com.tierra.auth.utils;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;

@Service
public class EmailService {
	


//    // TESTS Mail Config
//    private final SendGrid sendGrid;
//    private final String fromEmail;
//
//    public EmailService(@Value("${sendgrid.api-key}") String apiKey,
//                        @Value("${sendgrid.from-email}") String fromEmail) {
//        this.sendGrid = new SendGrid(apiKey);
//        this.fromEmail = fromEmail; // must be a verified sender in SendGrid
//    }
// 
//
//
//    public String sendOtpForForgotPassword(String toEmail) {
//        String otp = generateOtp();
//
//        String subject = "Tierra: Agnivesha Entrance Coaching Classes - OTP for Forgot Password Reset";
//        String body = "Dear User,\n\n"
//                + "You have requested to reset your password. Please use the following One-Time Password (OTP) to proceed:\n\n"
//                + "OTP: " + otp + "\n\n"
//                + "⚠️ This OTP is valid for 10 minutes only.\n"
//                + "If you did not request this, please ignore this email.\n\n"
//                + "Note: This is an automated email. Please do not reply to this message.\n\n"
//                + "Regards,\n"
//                + "Tierra: Agnivesha Entrance Coaching Classes Support Team";
//
//        sendEmail(toEmail, subject, body);
//        return otp;
//    }
//
//    public String sendOtpForNewUser(String toEmail) {
//        String otp = generateOtp();
//
//        String subject = "Tierra: Agnivesha Entrance Coaching Classes - OTP for Registration";
//        String body = "Dear User,\n\n"
//                + "You have requested to Register as New User. Please use the following One-Time Password (OTP) to proceed:\n\n"
//                + "OTP: " + otp + "\n\n"
//                + "Thank You for Registering.\n\n"
//                + "⚠️ This OTP is valid for 10 minutes only.\n"
//                + "If you did not request this, please ignore this email.\n\n"
//                + "Note: This is an automated email. Please do not reply to this message.\n\n"
//                + "Regards,\n"
//                + "Tierra: Agnivesha Entrance Coaching Classes Support Team";
//
//        sendEmail(toEmail, subject, body);
//        return otp;
//    }
//
//    private void sendEmail(String toEmail, String subject, String body) {
//        Email from = new Email(fromEmail);
//        Email to = new Email(toEmail);
//        Content content = new Content("text/plain", body);
//        Mail mail = new Mail(from, subject, to, content);
//
//        Request request = new Request();
//        try {
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//            Response response = sendGrid.api(request);
//
//            System.out.println("SendGrid Response: " + response.getStatusCode());
//            if (response.getStatusCode() >= 400) {
//                throw new RuntimeException("Failed to send email via SendGrid: " + response.getBody());
//            }
//        } catch (IOException ex) {
//            throw new RuntimeException("Error sending email via SendGrid", ex);
//        }
//    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otp);
    }
    
  @Autowired
  private JavaMailSender mailSender;

// Local Mail Config

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
}

