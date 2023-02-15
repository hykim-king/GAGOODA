package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.mapper.UserMapper;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class UserServiceImp implements UserService {
    private UserMapper userMapper;
    public UserServiceImp(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void sendMail(String receiverEmail, String title, String content) throws Exception {
        // Recipient's email ID needs to be mentioned.
        String from = "gagooda001@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "yocbeodancbfrshi");
            }
        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));

            // Set Subject: header field
            message.setSubject(title);

            // Now set the actual message
            message.setText(content);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
    public int register(UserDto user) {
        return userMapper.insertOne(user);
    }
    public UserDto login(String email, String pw) {
        return userMapper.findByEmailAndPw(email, pw);
    }
    public UserDto findpw(String email, String name) {
        UserDto user = userMapper.findByEmailAndName(email, name);
        if(user != null) {
            try {
                String title = "GAGOODA 쇼핑몰 계정 비밀번호 재설정";
                String content = "http://127.0.0.1:8888/user/"+user.getUserId()+"/password_reset.do";
                sendMail(email, title, content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public int modifyOne(UserDto user) {
        return userMapper.updateOne(user);
    }

    public UserDto selectOne(int userId) {
        return userMapper.findById(userId);
    }

    public UserDto doubleCheck(String email, String pw) {
        return userMapper.findByEmailAndPw(email, pw);
    }

    public int delete(int userId) {
        return userMapper.deleteById(userId);
    }

}
