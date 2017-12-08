package pageObject.utility;

import org.openqa.selenium.WebDriver;
import pageObject.mmjtraine.pages.HomePage;
import pageObject.mmjtraine.pages.Page;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;

public class TestClass {

    WebDriver webDriver;
    String test = "asdasdas";

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException {
        TestClass testClass = new TestClass();

        testClass.getObject();
        testClass.getModifierReflection();
        testClass.getSuperClass();
        testClass.getImplementedInterfaces();
        testClass.exploringOfClassFields();
        testClass.nameField();
        testClass.exploringConstructors();
        testClass.getFieldValue();
    }

    PropertyLoader propertyLoader = new PropertyLoader();
    HomePage homePage = new HomePage(webDriver);

    private void nameField() throws NoSuchFieldException {
        Class c = homePage.getClass();
        Field nameField = c.getField("WEB_URL");
        System.out.print("\n");
        System.out.println("Имя: " + nameField);
    }

    private void getFieldValue() throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {

        Class c = Class.forName("TestClass");
        Class cz = this.getClass();

        Field fieldName = cz.getDeclaredField("test");
//        Class aClass = myClassLoader.loadClass(c);


        Object fieldValue = fieldName.get(cz);



        System.out.print("\n");
        System.out.println("Значения: " + fieldValue);
    }

    private void exploringConstructors() throws NoSuchMethodException {
        Class c = homePage.getClass();
        Constructor[] constructors = c.getConstructors();
        System.out.print("\n");
        for (Constructor constructor : constructors) {
            Class[] paramTypes = constructor.getParameterTypes();
            for (Class paramType : paramTypes) {
                System.out.print(paramType.getName() + " ");
            }
            System.out.println();
        }

    }

    private void exploringOfClassFields() {
        Class c = homePage.getClass();
        Field[] publicFields = c.getFields();
        for (Field field : publicFields) {
            Class fieldType = field.getType();
            System.out.print("\n");
            System.out.println("Имя: " + field.getName());
            System.out.println("Тип: " + fieldType.getName());
        }
    }

    private void getImplementedInterfaces() {
        Class c = LinkedList.class;
        Class[] interfaces = c.getInterfaces();
        System.out.print("\n");
        for (Class cInterface : interfaces) {
            System.out.println(cInterface.getName());
        }
    }

    private void getSuperClass() {
        Class c = homePage.getClass();
        Class superclass = c.getSuperclass();
        System.out.print("\n");
        System.out.println(superclass);
    }

    private void getModifierReflection() {
        Class c = propertyLoader.getClass();
        int mods = c.getModifiers();
        System.out.print("\n");
        if (Modifier.isPublic(mods)) {
            System.out.println("public");
        }
        if (Modifier.isAbstract(mods)) {
            System.out.println("abstract");
        }
        if (Modifier.isFinal(mods)) {
            System.out.println("final");
        }
    }

    private void getObject() {
        Class aclass = propertyLoader.getClass();
        System.out.print("\n");
        System.out.println(aclass);
    }
}
