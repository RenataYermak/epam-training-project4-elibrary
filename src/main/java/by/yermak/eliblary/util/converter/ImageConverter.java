package by.yermak.eliblary.util.converter;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.UnsupportedEncodingException;


public final class ImageConverter {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CHARSET = "UTF-8";

    private ImageConverter(){}

    public static String imageToString(byte[] imageBytes) {
        String base64Encoded = null;
        try {
            byte[] encodeBase64 = Base64.encodeBase64(imageBytes);
            base64Encoded = new String(encodeBase64, CHARSET);
        } catch (UnsupportedEncodingException e) {
            LOGGER.log(Level.WARN,"ImageConverter imageToString can't be performed. {}",e.getMessage());
        }
        return base64Encoded;
    }
}