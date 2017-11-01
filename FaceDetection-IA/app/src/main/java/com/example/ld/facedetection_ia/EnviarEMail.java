package com.example.ld.facedetection_ia;

import android.icu.util.EthiopicCalendar;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.net.URI;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;


/**
 * Created by ld on 31/10/17.
 */

public class EnviarEMail {
    String correo;
    String contrasena;

    Session session;

    public void EnviarEmail()
    {
        this.contrasena="";
        this.correo="";

    }

    public void Enviar(int nroRostros){

        correo = "registro.rostros@gmail.com";
        contrasena = "Ip0d.N4n0";

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //se crea una property con los datos del servidor SMTP
                Properties properties = new Properties();
                properties.put("mail.smtp.host","smtp.gmail.com");
                properties.put("mail.smtp.socketFactory.port","465");
                properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                properties.put("mail.smtp.auth","true");
                properties.put("mail.smtp.port","465");

               try {
                   //se crea una sesión con los datos de usuario y contraseña
                    Session session = Session.getInstance(properties, new GMailAuthenticator(correo, contrasena));

                    if (session != null){
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(correo));
                        message.setSubject("Rostro Reconocido");
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("lucasgarcia86@gmail.com"));
                        message.setContent("Se han detectado " + nroRostros + " rostros diferentes", "text/html; charset=utf-8");

                        //se envía el correo
                        Transport t = session.getTransport("smtp");
                        t.connect(correo, contrasena);
                        t.sendMessage(message, message.getAllRecipients());
                        t.close();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

            }

}
