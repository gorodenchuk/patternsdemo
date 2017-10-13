package pageObject.utility.emails.letters;

import pageObject.utility.emails.FetchingEmail;

import javax.mail.MessagingException;

/**
 * Created by NGorodenchuk on 9/26/2017.
 */
public class MmmjTrainPasswordReset extends FetchingEmail {

    public MmmjTrainPasswordReset(String subjectKeyword) throws MessagingException {
        super(subjectKeyword);
    }

    public String getResetPasswordButtonLink() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("16px;\" href=\"") + 13, FetchingEmail.emailHtml.indexOf("\" target=\"_blank\""));
            System.out.println("Reset Password Button Link: " + result);
            System.out.println("-----------------------------------------------");
            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("'Reset password' Button, or letter doesnt recieved");
            return null;
        }
    }

    public String getResetPasswordLink() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("into your browser:") + 109, FetchingEmail.emailHtml.indexOf("We respect the privacy") - 197);
            System.out.println("Reset Password Link: " + result);
            System.out.println("-----------------------------------------------");
            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("'Reset password' link, or letter doesnt recieved");
            return null;
        }
    }

}
