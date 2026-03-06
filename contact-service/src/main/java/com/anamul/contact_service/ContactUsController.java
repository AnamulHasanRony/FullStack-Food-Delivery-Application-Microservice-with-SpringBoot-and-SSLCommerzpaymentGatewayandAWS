package com.anamul.contact_service;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contactUs")
@AllArgsConstructor
public class ContactUsController {
    private final ContactUsService contactUsService;

    @PostMapping
    public void submitContactUsForm(@RequestBody ContactUsRequest contactUsRequest){
        contactUsService.saveContactUsFormToDatabase(contactUsRequest);
    }
}
