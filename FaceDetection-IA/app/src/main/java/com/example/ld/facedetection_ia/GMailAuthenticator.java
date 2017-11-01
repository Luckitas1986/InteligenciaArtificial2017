package com.example.ld.facedetection_ia;

/**
 * Created by ld on 1/11/17.
 */
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class GMailAuthenticator extends Authenticator {
    String user;
    String pw;
    public GMailAuthenticator (String username, String password)
    {
        super();
        this.user = username;
        this.pw = password;
    }
    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(user, pw);
    }
}