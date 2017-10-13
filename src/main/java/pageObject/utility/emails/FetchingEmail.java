package pageObject.utility.emails;

/**
 * Created by NGorodenchuk on 9/25/2017.
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jsoup.Jsoup;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;

public class FetchingEmail {

    public static final String HOST = "pop.gmail.com";
    public static final String USERNAME = "mmjtrain.test@gmail.com";
    public static final String PASSWORD = "qwerty@12345";
    public static final String FROMEMAIL = "info@mmjtrain.com";
    private static boolean textIsHtml = false;
    private String subjectKeyword;
    public static String emailSubject;
    public static String emailFrom;
    public static String emailText;
    public static String emailHtml;

    public List<String> productsListOfItemName = new ArrayList<String>();
    public List<String> productsListOfItemPrice = new ArrayList<String>();
    public List<String> productsListOfItemQuantity = new ArrayList<String>();

    public FetchingEmail(String subjectKeyword) throws MessagingException {
        this.subjectKeyword = subjectKeyword;
    }

    public String getSubjectKeyword() {
        return subjectKeyword;
    }

    public void setSubjectKeyword(String subjectKeyword) {
        this.subjectKeyword = subjectKeyword;
    }

    public void fetch(FetchingEmail ob) throws MessagingException {

        try {

            //create properties field
            Properties properties = new Properties();

            properties.put("mail.pop3.host", HOST);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            store.connect(HOST, USERNAME, PASSWORD);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in));

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);

            for (int i = messages.length - 1; i >= messages.length - 10; i--) {

                Message message = messages[i];
                Address[] froms = message.getFrom();
                String email = froms == null ? null : ((InternetAddress) froms[0]).getAddress();
                if (message.getSubject() == null) {
                    continue;
                }
                try {
                    if (message.getSubject().contains(subjectKeyword) && email.equals(FROMEMAIL)) {

                        emailSubject = message.getSubject();
                        emailFrom = message.getFrom()[0].toString();
                        emailText = getTextFromMessage(message);
                        emailHtml = getText(message);

//                        System.out.println("---------------------------------");
//                        System.out.println("Email Number " + (i + 1));
//                        System.out.println("Subject: " + emailSubject);
//                        System.out.println("From: " + emailFrom);
//                        System.out.println("Text: " + emailText);
//                        System.out.println("html: " + emailHtml);
                        break;
                    }

                } catch (Exception expected) {
// TODO Auto-generated catch block
                    expected.printStackTrace();
                }
            }
            // disconnect
            emailFolder.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        }
    }

    public static String getTextFromMessage(Message message) throws Exception {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            String result = "";
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            int count = mimeMultipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    result = result + "\n" + bodyPart.getContent();
                    break;  //without break same text appears twice in my tests
                } else if (bodyPart.isMimeType("text/html")) {
                    String html = (String) bodyPart.getContent();
                    result = result + "\n" + Jsoup.parse(html).text();

                }
            }
            return result;
        }
        return "";
    }

    private static String getText(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String) p.getContent();
            textIsHtml = p.isMimeType("text/html");
            return s;
        }
        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart) p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }
        return null;
    }

    public String getUserName() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("Hi, ") + 4, FetchingEmail.emailHtml.indexOf("</h2>"));
            System.out.println("User name: " + result);
            System.out.println("-----------------------------------------------");
            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("User name is missed, or letter doesnt recieved");
            return null;
        }
    }

    public String getTotalPrice() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("#48738c;\">$") + 11, FetchingEmail.emailHtml.indexOf("</span></p>"));
            System.out.println("Total price: " + result);
            System.out.println("-----------------------------------------------");
            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Total price is missed, or letter doesnt recieved");
            return null;
        }
    }

    public void setOrderedProductName() {
        String result = FetchingEmail.emailHtml;
        try {
            int i = 0;
            while (i < result.length()) {
                int found = result.indexOf("#48738c; margin: 0;\">", i);
                if (found == -1) break;
                int start = found + 21; // start of actual name
                int end = result.indexOf("</p><p", start);
                productsListOfItemName.add(result.substring(start, end));
                System.out.println("-----------------------------------------------");
                System.out.println("Ordered product name: " + result.substring(start, end));
                i = end + 6;  // advance i to start the next iteration
            }
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Ordered product name is missed, or letter doesnt recieved");
        }
    }

    public void setOrderedProductQuantity() {
        String result = FetchingEmail.emailHtml;
        try {
            int i = 0;
            while (i < result.length()) {
                int found = result.indexOf("Quantity: ", i);
                if (found == -1) break;
                int start = found + 10; // start of actual name
                int end = result.indexOf("</p></td><td", start);
                productsListOfItemQuantity.add(result.substring(start, end));
                System.out.println("-----------------------------------------------");
                System.out.println("Ordered product quantity: " + result.substring(start, end));
                i = end + 13;  // advance i to start the next iteration
            }
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Ordered product quantity is missed, or letter doesnt recieved");
        }
    }

    public void setOrderedProductPrice() {
        String result = FetchingEmail.emailHtml;
        try {
            int i = 0;
            while (i < result.length()) {
                int found = result.indexOf(" 20px;\">$", i);
                if (found == -1) break;
                int start = found + 9; // start of actual name
                int end = result.indexOf("</td></tr>", start);
                productsListOfItemPrice.add("$" + result.substring(start, end));
                System.out.println("-----------------------------------------------");
                System.out.println("Ordered product item price: " + result.substring(start, end));
                i = end + 10;  // advance i to start the next iteration
            }
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Ordered product price is missed, or letter doesnt recieved");
        }
    }

    public int nthIndexOf(String sought, int n) {
        String source = FetchingEmail.emailHtml;
        int index = source.indexOf(sought);
        if (index == -1) return -1;

        for (int i = 1; i < n; i++) {
            index = source.indexOf(sought, index + 1);
            if (index == -1) return -1;
        }
        return index;
    }

    public String getFullName() {
        try {
            int firstIndexOfSearchedText = nthIndexOf("<p style=\"margin: 0;\">", 2);
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("Delivery Address:") + 115, firstIndexOfSearchedText - 6);
            System.out.println("Full name: " + result);
            System.out.println("-----------------------------------------------");
            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Full name is missed, or letter doesnt recieved");
            return null;
        }
    }

    public String getDeliveryAddress() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("Delivery Address:") + 164, FetchingEmail.emailHtml.indexOf("Delivery Method:") - 92);
            String nresult = result.replace("<br>", "\n");
            System.out.println("Delivery address: " + nresult);
            System.out.println("-----------------------------------------------");
            return nresult;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Delivery address is missed, or letter doesnt recieved");
            return null;
        }
    }

    public String getSignature() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("Signature Required:") + 93, FetchingEmail.emailHtml.indexOf("0 10px;\">Order</h2>") - 178);
            System.out.println("-----------------------------------------------");
            System.out.println("Signature: " + result);
            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Signature is missed, or letter doesnt recieved");
            return null;
        }
    }

    public void getNameQuantityPrice() {
        for (int i = 0; i < productsListOfItemName.size(); i++) {
            System.out.println("-----------------------------------------------");
            System.out.println("NAME " + productsListOfItemName.get(i) + "\n" +
                    "QUANTITY " + productsListOfItemQuantity.get(i) + "\n" +
                    "PRICE " + productsListOfItemPrice.get(i));
        }
    }

    public String getOrderNumber() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("Order Number:") + 87, FetchingEmail.emailHtml.indexOf("Delivery Address:") - 86);
            System.out.println("-----------------------------------------------");
            System.out.println("Order number: " + result);
            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Order number is missed, or letter doesnt recieved");
            return null;
        }
    }

    public String getDeliverMethod() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("Delivery Method:") + 90, FetchingEmail.emailHtml.indexOf("Delivery Instructions:") - 86);
            String nresult = result.replace("<br>", " ");
            System.out.println("Deliver method: " + nresult);
            System.out.println("-----------------------------------------------");
            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Deliver method is missed, or letter doesnt recieved");
            return null;
        }
    }

    public String getDeliverInstruction() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("Delivery Instructions:") + 96, FetchingEmail.emailHtml.indexOf("Signature Required:") - 86);
            System.out.println("Deliver Instructions: " + result);
            System.out.println("-----------------------------------------------");
            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Deliver Instructions is missed, or letter doesnt recieved");
            return null;
        }
    }

}
