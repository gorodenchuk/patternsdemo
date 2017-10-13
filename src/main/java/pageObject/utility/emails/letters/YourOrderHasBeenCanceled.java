package pageObject.utility.emails.letters;

import pageObject.utility.emails.FetchingEmail;

import javax.mail.MessagingException;

/**
 * Created by NGorodenchuk on 9/26/2017.
 */
public class YourOrderHasBeenCanceled extends FetchingEmail {

    public YourOrderHasBeenCanceled(String subjectKeyword) throws MessagingException {
        super(subjectKeyword);
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


}
