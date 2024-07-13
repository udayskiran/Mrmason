package com.application.mrmason.service;

public interface SmsSender {

    boolean sendSMSMessage(String phoneNumber, String otp);
}
