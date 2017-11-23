package pageObject.mmjtraine.businessobject.useresfacade;

public class UserHelper {

    private IUsers admin;
    private IUsers manager;
    private IUsers shipper;

    public UserHelper(){
        admin = new Admin();
        manager = new Manager();
        shipper = new Shipper();
    }

    public String readAdminUserEmail() {
        return admin.readEmail();
    }

    public String readManagerUserEmail() {
        return manager.readEmail();
    }

    public String readShipperUserEmail() {
        return shipper.readEmail();
    }

    public String readAdminUserPassword() {
        return admin.readPassword();
    }

    public String readManagerUserPassword() {
        return manager.readPassword();
    }

    public String readShipperUserPassword() {
        return shipper.readPassword();
    }

    public static void main(String[] args) {
        UserHelper userHelper = new UserHelper();
        userHelper.readAdminUserEmail();
        userHelper.readManagerUserEmail();
        userHelper.readShipperUserEmail();



        userHelper.readAdminUserPassword();
        userHelper.readManagerUserPassword();
        userHelper.readShipperUserPassword();
    }
}
