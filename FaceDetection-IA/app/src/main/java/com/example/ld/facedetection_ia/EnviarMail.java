package com.example.ld.facedetection_ia;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



/**
 * Created by ld on 31/10/17.
 */

public class EnviarMail extends AppCompatActivity {
    String correo;
    String contrasena;

    EditText mensaje;
    Button enviar;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enviar_mail);

        mensaje = (EditText) findViewById(R.id.mensaje);
        enviar = (Button) findViewById(R.id.enviar);

        correo = "lucasgarcia86@gmail.com";
        contrasena = "Ip0d*N4n0.";

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
             /**   try {
                    sendPlainTextEmail("smtp.gmail.com","587", "lucasgarcia86@gmail.com","Ip0d*N4n0.","lucasgarcia86@gmail.com", "TEST", "TEST");
                } catch (MessagingException e) {
                    e.printStackTrace();
                }*/

                Properties properties = new Properties();
                properties.put("mail.smtp.host","smtp.gmail.com");
                properties.put("mail.smtp.socketFactory.port","465");
                properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                properties.put("mail.smtp.auth","true");
                properties.put("mail.smtp.port","465");

               try {
                    Session session = Session.getInstance(properties, new GMailAuthenticator(correo, contrasena));

                    if (session != null){
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(correo));
                        message.setSubject("Rostro Reconocido");
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("lucasgarcia86@gmail.com"));
                        message.setContent(mensaje.getText().toString(), "text/html; charset=utf-8");

                        //Transport.send(message);
                        Transport t = session.getTransport("smtp");
                        t.connect(correo, contrasena);
                        t.sendMessage(message, message.getAllRecipients());
                        t.close();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }

}
