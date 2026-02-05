package com.tiuon.moneymanager.service;

public interface IEmailService {
    void sendEmail(String to, String subject, String body);
}
