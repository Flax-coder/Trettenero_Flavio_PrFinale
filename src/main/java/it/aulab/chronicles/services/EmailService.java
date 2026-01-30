package it.aulab.chronicles.services;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String text);
}
