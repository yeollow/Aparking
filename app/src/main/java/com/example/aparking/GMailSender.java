package com.example.aparking;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GMailSender extends javax.mail.Authenticator {
    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;
    private String emailCode;

    public GMailSender(String user, String password) {
        this.user = user;
        this.password = password;
        emailCode = createEmailCode();

        Authenticator auth = new SMTPAuthenticator("구글 계정", "구글 app 비밀번호");
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

        //구글에서 지원하는 smtp 정보를 받아와 MimeMessage 객체에 전달해준다.
        session = Session.getDefaultInstance(props, auth);

    }


    public String getEmailCode() {
        return emailCode;
    } //생성된 이메일 인증코드 반환

    private String createEmailCode() { //이메일 인증코드 생성
        String[] str = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String newCode = new String();

        for (int x = 0; x < 6; x++) {
            int random = (int) (Math.random() * str.length);
            newCode += str[random];
        }

        return newCode;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        //해당 메서드에서 사용자의 계정(id & password)을 받아 인증받으며 인증 실패시 기본값으로 반환됨.
        return new PasswordAuthentication(user, password);
    }

    public synchronized void sendMail(String subject, String body, String recipients) throws Exception {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(user));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message); //메시지 전달
    }


    private static class SMTPAuthenticator extends javax.mail.Authenticator {
        String id;
        String pwd;

        SMTPAuthenticator(String id, String pwd) {
            this.id = id;
            this.pwd = pwd;
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("ljy2784437", "igmytbfweuzzttpw");
        }
    }
}


