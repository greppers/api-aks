package tn.esprit.pidev.Services;


import tn.esprit.pidev.Payload.Mail;

public interface EmailService {

    public void sendCodeByMail(Mail mail);
}
