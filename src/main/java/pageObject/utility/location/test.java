package pageObject.utility.location;

import pageObject.utility.location.mode.ServerLocation;

import java.io.IOException;

import static pageObject.utility.location.Geocode.getLocationInfo;

/**
 * Created by NGorodenchuk on 7/7/2017.
 */
public class test {
    public static void main(String[] args) throws IOException {
        System.out.println(getLocationInfo(50.5662822,21.9462466));
        System.out.println("__________________________________________________");

        GetLocationExample obj = new GetLocationExample();
        ServerLocation location = obj.getLocation("95.50.165.134");
        System.out.println(location);
    }
}
