package pageObject.mmjtraine.businessobject.useresfacade;

import pageObject.utility.PropertyLoader;

public class Shipper implements IUsers {

    PropertyLoader propertyLoader = new PropertyLoader();

    @Override
    public String readEmail() {
        propertyLoader.propFile = "src\\test\\java\\resources\\testdata\\shipper_login.properties";
        return propertyLoader.loadProperty("shipper1.email", propertyLoader.propFile);
    }

    @Override
    public String readPassword() {
        propertyLoader.propFile = "src\\test\\java\\resources\\testdata\\shipper_login.properties";
        return propertyLoader.loadProperty("shipper1.password", propertyLoader.propFile);
    }
}
