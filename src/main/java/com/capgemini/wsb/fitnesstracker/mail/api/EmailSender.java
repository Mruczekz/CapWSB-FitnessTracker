package com.capgemini.wsb.fitnesstracker.mail.api;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * API interface for component responsible for sending emails.
 */
@Component
public interface EmailSender extends JavaMailSender {

    /**
     * Sends the email message to the recipient from the provided {@link EmailDto}.
     *
     * @param email information on email to be sent
     */
    void send(EmailDto email);

}
