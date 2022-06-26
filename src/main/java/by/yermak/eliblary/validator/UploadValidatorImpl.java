package by.yermak.eliblary.validator;

import javax.servlet.http.Part;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.yermak.eliblary.dao.mapper.ColumnName.PICTURE;
import static by.yermak.eliblary.util.locale.MessagesKey.MESSAGE_PICTURE_WRONG;

public class UploadValidatorImpl implements UploadValidator {
    public static final String PICTURE_EXTENSION_PATTERN = "([^\\s]+(\\.(?i)(jpg|jpeg|png))$)";
    private static UploadValidator instance;

    private UploadValidatorImpl() {
    }

    public static UploadValidator getInstance() {
        if (instance == null) {
            instance = new UploadValidatorImpl();
        }
        return instance;
    }

    @Override
    public Map<String, String> validateUpload(List<Part> parts) {
        Map<String, String> validationResult = new HashMap<>();
        if (parts.isEmpty() || !parts.get(0).getSubmittedFileName().matches(PICTURE_EXTENSION_PATTERN) ||
            parts.stream().anyMatch(part -> (part.getSize() / (1024 * 1024)) >= 10)) {
            validationResult.put(PICTURE, MESSAGE_PICTURE_WRONG);
        }
        return validationResult;
    }
}