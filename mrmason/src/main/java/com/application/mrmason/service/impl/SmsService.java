package com.application.mrmason.service.impl;


import com.application.mrmason.security.AppProperties;
import com.application.mrmason.service.SmsSender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Service
@Slf4j
@RequiredArgsConstructor
public class SmsService implements SmsSender {

    private final AppProperties appProperties;


    @Override
    public boolean sendSMSMessage(String phoneNumber, String otp) {
        try {
            // Construct message content with OTP
            String message = "Thanks for registering with us. Your OTP to verify your mobile number is " + otp + " - www.mrmason.in";

            // Encode message content and other parameters
            String apiKey = URLEncoder.encode(appProperties.getApiKey(), StandardCharsets.UTF_8.toString());
            String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
            String sender = URLEncoder.encode(appProperties.getSender(), StandardCharsets.UTF_8.toString());
            String numbers = URLEncoder.encode(phoneNumber, StandardCharsets.UTF_8.toString());

            // Construct URL
            String url = appProperties.getUrl() + "apikey=" + apiKey + "&numbers=" + numbers + "&message=" + encodedMessage + "&sender=" + sender;

            // Create HTTP connection
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Read response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder smsResponse = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                smsResponse.append(line).append(" ");
            }
            rd.close();

            // Log response
            log.info("Response From SMS Service: {}", smsResponse);
            return true;
        } catch (Exception e) {
            log.error("Exception while Sending SMS to Mobile Number {} with error: {}", phoneNumber, e.getMessage());
            return false;
        }
    }

}
