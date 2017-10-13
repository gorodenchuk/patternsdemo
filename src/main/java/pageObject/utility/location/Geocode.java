package pageObject.utility.location;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class return  street_address, formatted_address, street_address, postal_code, formatted_address
 *
 *
 * latitude, longitude based on IP address
 */

public class Geocode {

    private static final Logger LOG = LogManager.getLogger(Geocode.class);


    public static JSONObject getLocationInfo(double lat, double lng) {

        HttpGet httpGet = new HttpGet("http://maps.googleapis.com/maps/api/geocode/json?latlng="+ lat+","+lng +"&sensor=true");
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static String getCurrentLocationViaJSON(double lat, double lng) {

        JSONObject jsonObj = getLocationInfo(lat, lng);
        LOG.info("JSON string =>", jsonObj.toString());

        String currentLocation = "testing";
        String street_address = null;
        String postal_code = null;

        try {
            String status = jsonObj.getString("status").toString();
            LOG.info("status", status);

            if(status.equalsIgnoreCase("OK")){
                JSONArray results = jsonObj.getJSONArray("results");
                int i = 0;
                LOG.info("i", i+ "," + results.length() ); //TODO delete this
                do{

                    JSONObject r = results.getJSONObject(i);
                    JSONArray typesArray = r.getJSONArray("types");
                    String types = typesArray.getString(0);

                    if(types.equalsIgnoreCase("street_address")){
                        street_address = r.getString("formatted_address").split(",")[0];
                        LOG.info("street_address", street_address);
                    }else if(types.equalsIgnoreCase("postal_code")){
                        postal_code = r.getString("formatted_address");
                        LOG.info("postal_code", postal_code);
                    }

                    if(street_address!=null && postal_code!=null){
                        currentLocation = street_address + "," + postal_code;
                        LOG.info("Current Location =>", currentLocation); //Delete this
                        i = results.length();
                    }

                    i++;
                }while(i<results.length());

                LOG.info("JSON Geo Locatoin =>", currentLocation);
                return currentLocation;
            }

        } catch (JSONException e) {
            LOG.error("testing","Failed to load JSON");
            e.printStackTrace();
        }
        return null;
    }

}

//https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&key=AIzaSyAkEzbx6ujg516KzjN1OcZ27UaG1LNqSh4