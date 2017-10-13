package pageObject.utility.emails.letters;

import pageObject.utility.emails.FetchingEmail;

import javax.mail.MessagingException;

/**
 * Created by NGorodenchuk on 9/26/2017.
 */
public class ThankYouForPlacingYourOrderWithMmjTrain extends FetchingEmail {

    public ThankYouForPlacingYourOrderWithMmjTrain(String subjectKeyword) throws MessagingException {
        super(subjectKeyword);
    }

    @Override
    public String getUserName() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("Dear ") + 5, FetchingEmail.emailHtml.indexOf("Thanks for shopping with us") - 118);
            System.out.println("-----------------------------------------------");
            System.out.println("User name: " + result);

            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("User name is missed, or letter doesnt recieved");
            return null;
        }
    }

}
