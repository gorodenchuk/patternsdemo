package pageObject.utility.emails.letters;

import pageObject.utility.emails.FetchingEmail;

import javax.mail.MessagingException;

/**
 * Created by NGorodenchuk on 9/26/2017.
 */
public class YourMmjTrainOrderHasNowBeenShipped extends FetchingEmail {

    public YourMmjTrainOrderHasNowBeenShipped(String subjectKeyword) throws MessagingException {
        super(subjectKeyword);
    }

    @Override
    public String getUserName() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("Dear ") + 5, FetchingEmail.emailHtml.indexOf("</h2>"));
            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("User name is missed, or letter doesnt recieved");
            return null;
        }
    }

    @Override
    public String getOrderNumber() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("#48738c;\">") + 10, FetchingEmail.emailHtml.indexOf("</span>"));
            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Order number is missed, or letter doesnt recieved");
            return null;
        }
    }

    public String getTrackingNumber() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("number <span style=\"color: #48738c;\">") + 37, FetchingEmail.emailHtml.indexOf("</span>.<br"));
            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Tracking number is missed, or letter doesnt recieved");
            return null;
        }
    }

    public String getTrackYourPackageLink() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("16px;\" href=\"") + 13, FetchingEmail.emailHtml.indexOf("\" target=\""));
            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Track your package link is missed, or letter doesnt recieved");
            return null;
        }
    }

    public String getTotalPrice() {
        try {
            String result = FetchingEmail.emailHtml.substring(FetchingEmail.emailHtml.indexOf("#48738c;\">$") + 11, FetchingEmail.emailHtml.indexOf("</span></p>"));
            Integer.parseInt(result);
            return result;
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Total price is missed, or letter doesnt recieved");
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

}
