package pageObject.utility.emails.letters;

import pageObject.utility.emails.FetchingEmail;

import javax.mail.MessagingException;

/**
 * Created by NGorodenchuk on 9/26/2017.
 */
public class TheStatusOfYourRecommendationIsDeclined extends FetchingEmail {

    public TheStatusOfYourRecommendationIsDeclined(String subjectKeyword) throws MessagingException {
        super(subjectKeyword);
    }

    public String getStatus() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("#ee4c4c;\">") + 10, FetchingEmail.emailHtml.indexOf("</span>"));
            System.out.println("Recomendation status: " + result);
            System.out.println("-----------------------------------------------");
            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Recomendation status, or letter doesnt recieved");
            return null;
        }
    }

    public String getVisitMyAccountLink() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("16px;\" href=\"") + 13, FetchingEmail.emailHtml.indexOf("\" target=\"_blank"));
            System.out.println("Account link: " + result);
            System.out.println("-----------------------------------------------");
            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Account link, or letter doesnt recieved");
            return null;
        }
    }

}
