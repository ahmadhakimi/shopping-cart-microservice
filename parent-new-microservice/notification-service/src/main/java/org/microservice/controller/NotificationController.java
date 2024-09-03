package org.microservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.microservice.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/notify")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final EmailService emailService;
    @PostMapping
    public String testEmail(){
        emailService.sendEmail("hakimihakimi611@gmail.com", "testing email", "This is just testing the email");
        return "Sending email successfully";

    }
}
