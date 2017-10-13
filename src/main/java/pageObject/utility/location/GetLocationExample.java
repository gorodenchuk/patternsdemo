package pageObject.utility.location;


import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.maxmind.geoip.regionName;
import pageObject.utility.location.mode.ServerLocation;

import java.io.File;
import java.io.IOException;

/**
 * This class return  countryCode, countryName, region, countryCode, city,
 * postalCode, latitude, longitude based on IP address
 */
public class GetLocationExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		GetLocationExample obj = new GetLocationExample();
		ServerLocation location = obj.getLocation("217.20.186.161");
		System.out.println(location);
	}

	/**
	 * @param ipAddress
	 * @return
	 */
	public ServerLocation getLocation(String ipAddress) {
		File file = new File("C:\\Users\\NGorodenchuk.ARTFULBITS\\workplace\\com.traine\\src\\main\\java\\resources\\GeoLiteCity.dat");
		return getLocation(ipAddress, file);

	}

	/**
	 * @param ipAddress
	 * @param file
	 * @return
	 */
	public ServerLocation getLocation(String ipAddress, File file) {

		ServerLocation serverLocation = null;
		String loc;

		try {

			serverLocation = new ServerLocation();

			LookupService lookup = new LookupService(file, LookupService.GEOIP_MEMORY_CACHE);
			Location locationServices = lookup.getLocation(ipAddress);

//			serverLocation.setCountryCode(locationServices.countryCode);
//			serverLocation.setCountryName(locationServices.countryName);
//			serverLocation.setRegion(locationServices.region);
//			serverLocation.setRegionName(regionName.regionNameByCode(locationServices.countryCode, locationServices.region));
			serverLocation.setCity(locationServices.city);
//			serverLocation.setPostalCode(locationServices.postalCode);
//			serverLocation.setLatitude(String.valueOf(locationServices.latitude));
//			serverLocation.setLongitude(String.valueOf(locationServices.longitude));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		return serverLocation;
	}

}
