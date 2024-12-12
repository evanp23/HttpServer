package HttpServer.JSON;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JSONException extends Exception{

    Logger logger;
    JSONException(String message, Class callingClazz, Exception originalException){
        logger = LogManager.getLogger(callingClazz);
        logger.error(message);
        originalException.printStackTrace(System.out);
    }
}
