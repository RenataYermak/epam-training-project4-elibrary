package by.yermak.eliblary.validator;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Map;

public interface UploadValidator {
    /**
     * Validates uploaded data parts
     *
     * @param parts a list of parts that was received within a multipart/from-data POST request
     * @return whether uploaded data parts are valid
     */
    Map<String, String> validateUpload(List<Part> parts);
}

