package server;
 
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class YoolooLogger {
 
    private static final Logger LOGGER = Logger.getLogger(YoolooLogger.class.getName());
    public static void main(String[] args) throws SecurityException, IOException {
 
        LOGGER.log(Level.INFO, "Logger Name: {0}", LOGGER.getName());
         
        LOGGER.warning("Can cause ArrayIndexOutOfBoundsException");
         
        //An array of size 3
        int []a = {1,2,3};
        int index = 4;
        LOGGER.log(Level.CONFIG, "index is set to {0}", index);
         
        try{
            System.out.println(a[index]);
        }catch(ArrayIndexOutOfBoundsException ex){
            LOGGER.log(Level.SEVERE, "Exception occur", ex);
        }
         
 
    }
 
}