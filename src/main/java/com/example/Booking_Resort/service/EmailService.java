package com.example.Booking_Resort.service;

import com.example.Booking_Resort.dto.MainBody;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {

    JavaMailSender mailSender;

    public void sendOtpEmail(MainBody mainBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mainBody.to());
        message.setFrom("23520699@gmail.com");
        message.setSubject(mainBody.subject());
        message.setText(mainBody.text());
        mailSender.send(message);
    }
}
