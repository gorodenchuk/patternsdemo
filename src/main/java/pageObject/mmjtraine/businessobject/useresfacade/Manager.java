package pageObject.mmjtraine.businessobject.useresfacade;

import pageObject.utility.PropertyLoader;

public class Manager implements IUsers {

    PropertyLoader propertyLoader = new PropertyLoader();

    @Override
    public String readEmail() {
        propertyLoader.propFile = "src\\test\\java\\resources\\testdata\\manager_login.properties";
        return propertyLoader.loadProperty("manager1.email", propertyLoader.propFile);
    }

    @Override
    public String readPassword() {
        propertyLoader.propFile = "src\\test\\java\\resources\\testdata\\manager_login.properties";
        return propertyLoader.loadProperty("manager1.password", propertyLoader.propFile);
    }

}
