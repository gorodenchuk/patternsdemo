package pageObject.utility.emails.letters;

import pageObject.utility.emails.FetchingEmail;

import javax.mail.MessagingException;

/**
 * Created by NGorodenchuk on 9/27/2017.
 */
public class MmjTrainOrderHasBeenProcessed extends FetchingEmail {

    public MmjTrainOrderHasBeenProcessed(String subjectKeyword) throws MessagingException {
        super(subjectKeyword);
    }

    @Override
    public String getUserName() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("Dear ") + 5, FetchingEmail.emailHtml.indexOf("</h2>"));
            System.out.println("-----------------------------------------------");
            System.out.println("User name: " + result);
            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("User name is missed, or letter doesnt recieved");
            return null;
        }
    }

    @Override
    public String getTotalPrice() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("#48738c;\">$") + 11, FetchingEmail.emailHtml.indexOf(" for order") - 7);
            System.out.println("-----------------------------------------------");
            System.out.println("Total price: " + result);
            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Total price is missed, or letter doesnt recieved");
            return null;
        }
    }

    @Override
    public String getOrderNumber() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf(" for order ") + 41, FetchingEmail.emailHtml.indexOf("has been successfully processed") - 38);
            System.out.println("-----------------------------------------------");
            System.out.println("Order number: " + result);
            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Order number is missed, or letter doesnt recieved");
            return null;
        }
    }

}
