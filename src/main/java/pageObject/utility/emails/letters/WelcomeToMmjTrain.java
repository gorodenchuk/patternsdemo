package pageObject.utility.emails.letters;

import pageObject.utility.emails.FetchingEmail;

import javax.mail.MessagingException;

/**
 * Created by NGorodenchuk on 9/21/2017.
 */
public class WelcomeToMmjTrain extends FetchingEmail {

    public WelcomeToMmjTrain(String subjectKeyword) throws MessagingException {
        super(subjectKeyword);
    }

    public String getStartShoppingButtonLink() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("ing: 14px 0 16px;\" href=\"") + 25, FetchingEmail.emailHtml.indexOf("START SHOPPING") - 18);
            System.out.println("Start Shopping Button Link: " + result);
            System.out.println("-----------------------------------------------");
            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Recomendation status, or letter doesnt recieved");
            return null;
        }
    }

}
