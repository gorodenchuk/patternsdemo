package pageObject.mmjtraine.businessobject.useresfacade;

import pageObject.utility.PropertyLoader;

import java.io.FileReader;

public class Admin extends PropertyLoader implements IUsers {

    PropertyLoader propertyLoader = new PropertyLoader();

    @Override
    public String readEmail() {
        propertyLoader.propFile = "src\\test\\java\\resources\\testdata\\admin_login.properties";
        return propertyLoader.loadProperty("superadmin1.email", propertyLoader.propFile);
    }

    @Override
    public String readPassword() {
        propertyLoader.propFile = "src\\test\\java\\resources\\testdata\\admin_login.properties";
        return propertyLoader.loadProperty("superadmin1.password", propertyLoader.propFile);
    }
}
