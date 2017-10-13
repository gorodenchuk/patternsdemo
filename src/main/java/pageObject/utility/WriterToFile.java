package pageObject.utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by gorod on 06.12.2016.
 */
public class WriterToFile {

        public static void writeArrayToFile(String fileToWrite, List<String> arrayListToWrite) throws IOException {
            FileWriter fw = new FileWriter(fileToWrite);
            BufferedWriter bw = new BufferedWriter(fw);
            for (String s : arrayListToWrite){
                bw.write(s + "\n");//write text to the file using write method
            }
            // bw.flush();//write any buffered text to the file
            bw.close();//close file using close() method
        }


}
