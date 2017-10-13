package pageObject.utility;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by gorod on 15.11.2016.
 */
public class CsvDataProvider {
    private static final Logger LOG = LogManager.getLogger(CsvDataProvider.class);

    @DataProvider(name = "CsvDataProvider")
    public static Iterator<Object[]> provideData(Method method){
        List<Object[]> list = new ArrayList<Object[]>();
        String pathName = "test_data" + File.separator + method.getDeclaringClass().getSimpleName() + "." + method.getName() + ".csv";
//        String pathName = "C:\\Users\\NGorodenchuk.ARTFULBITS\\workplace\\com.traine\\test_data\\ParticularSignUpTestSuit.checkSignUpWithCorrectData1.csv";
        File file = new File(pathName);
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
//            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(file)));
            String[] keys = reader.readNext();
            if (keys != null){
                String[] dataParts;
                while ((dataParts = reader.readNext()) != null){

                    Map<String, String> testData = new HashMap<String, String>();
                    for (int i=0; i < keys.length; i++) {
//                        LOG.info(keys[i] + " " + dataParts[i]);
                        testData.put(keys[i], dataParts[i]);
                    }
                    list.add(new Object[]{testData});
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File " + pathName + " was not found.\n" + e.getStackTrace().toString());
        }
        catch (IOException e) {
            throw new RuntimeException("Could not read " + pathName + " was not found.\n" + e.getStackTrace().toString());
        }
        return list.iterator();
    }

}