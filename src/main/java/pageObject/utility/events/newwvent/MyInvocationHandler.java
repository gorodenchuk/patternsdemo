package pageObject.utility.events.newwvent;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class MyInvocationHandler implements InvocationHandler {

    private WebDriver webDriver;

    public MyInvocationHandler(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("method name = " + method.getName());
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            System.out.println("arg[" + i + "] = " + arg);
        }
        return method.invoke(webDriver, args);
    }

    public static void main(String[] args) {
        HtmlUnitDriver aDriver = new HtmlUnitDriver();

        WebDriver proxyWebDriver = (WebDriver) Proxy.newProxyInstance(MyInvocationHandler.class.getClassLoader(),
                new Class[]{WebDriver.class}, new MyInvocationHandler(aDriver));

        proxyWebDriver.get("http://google.com");

    }
}