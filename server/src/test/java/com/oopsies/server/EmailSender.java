// package com.oopsies.server;

// import java.io.File;
// import java.net.URI;
// import java.util.Properties;
// import java.io.IOException;

// import jakarta.mail.Authenticator;
// import jakarta.mail.BodyPart;
// import jakarta.mail.Message;
// import jakarta.mail.Multipart;
// import jakarta.mail.MessagingException;
// import jakarta.mail.PasswordAuthentication;
// import jakarta.mail.Session;
// import jakarta.mail.Transport;
// import jakarta.mail.internet.InternetAddress;
// import jakarta.mail.internet.MimeBodyPart;
// import jakarta.mail.internet.MimeMessage;
// import jakarta.mail.internet.MimeMultipart;

// public class EmailSender {

//     private String username;
//     private String password;
//     private String host;
//     private int port;

//     private final Properties prop;

//     public EmailSender(String host, int port, String username, String password) {
//         prop = new Properties();
//         prop.put("mail.smtp.auth", true);
//         prop.put("mail.smtp.starttls.enable", "true");
//         prop.put("mail.smtp.host", host);
//         prop.put("mail.smtp.port", port);
//         prop.put("mail.smtp.ssl.trust", host);

//         this.username = username;
//         this.password = password;
//     }

//     public EmailSender(String host, int port) {
//         prop = new Properties();
//         prop.put("mail.smtp.host", host);
//         prop.put("mail.smtp.port", port);
//     }


//     public Session getSession() {
//         Properties props = new Properties();
//         props.put("mail.smtp.auth", "true");
//         props.put("mail.smtp.starttls.enable", "true");
//         props.put("mail.smtp.host", this.host);
//         props.put("mail.smtp.port", this.port);

//         return Session.getInstance(props, new Authenticator() {
//             protected PasswordAuthentication getPasswordAuthentication() {
//                 return new PasswordAuthentication(username, password);
//             }
//         });
//     }

//     public void sendMail(Session session, String from, String to) throws MessagingException, IOException {
//         Message message = new MimeMessage(session);
//         message.setFrom(new InternetAddress(from));
//         message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//         message.setSubject("Testing Subject");

//         BodyPart messageBodyPart = new MimeBodyPart();
//         messageBodyPart.setText("This is message body");

//         Multipart multipart = new MimeMultipart();
//         multipart.addBodyPart(messageBodyPart);

//         MimeBodyPart attachmentPart = new MimeBodyPart();
//         attachmentPart.attachFile(getFile("attachment.txt"));
//         multipart.addBodyPart(attachmentPart);

//         MimeBodyPart attachmentPart2 = new MimeBodyPart();
//         attachmentPart2.attachFile(getFile("attachment2.txt"));
//         multipart.addBodyPart(attachmentPart2);

//         message.setContent(multipart);
//         Transport.send(message);
//     }

//     private File getFile(String filename) {
//         try {
//             URI uri = this.getClass()
//               .getClassLoader()
//               .getResource(filename)
//               .toURI();
//             return new File(uri);
//         } catch (Exception e) {
//             throw new IllegalArgumentException("Unable to find file from resources: " + filename);
//         }
//     }

//     public static void main(String[] args) {
//         String MyUsername = "laiu.asher@gmail.com";
//         String MyPassword = "18081808";
//         String MyHost = "smtp.gmail.com";
//         int MyPost = 587;

//         EmailSender emailSender = new EmailSender(MyHost, MyPost, MyUsername, MyPassword);

//         Session session = emailSender.getSession();

//         try {
//             emailSender.sendMail("laiu.asher@gmail.com", "asher.laiu.2022@economics.smu.edu.sg");

//         } catch(MessagingException | IOException e) {
//             System.out.println("Error in sending email");
//             e.printStackTrace();

//         }
//     }
// }

// ASHER THIS FILE IS BROKEN, MIGHT HAVE TO RELOOK. 