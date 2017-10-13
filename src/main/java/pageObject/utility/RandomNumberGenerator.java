package pageObject.utility;

import java.util.Random;

/**
 * Created by gorod on 19.07.2017.
 */
public class RandomNumberGenerator {

    static String randomNumber;

    public static void generateRandomInteger() {

        int aStart = 1000;
        int aEnd = 9999;
        Random aRandom = new Random();

        if (aStart > aEnd) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        long range = (long) aEnd - (long) aStart + 1;
        long fraction = (long) (range * aRandom.nextDouble());
        int number = (int) (fraction + aStart);
        randomNumber = String.valueOf(number);

    }

    public static String getGeneratedNumber (){
        return randomNumber;
    }

}

