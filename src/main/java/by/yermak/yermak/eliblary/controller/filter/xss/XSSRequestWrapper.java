package by.yermak.yermak.eliblary.controller.filter.xss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XSSRequestWrapper extends HttpServletRequestWrapper {
    private static final String CLOSE_TAG = ">";
    private static final String OPEN_TAG = "<";
    private static final String EMPTY_STRING = "";

    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = stripXSS(values[i]);
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        return stripXSS(super.getParameter(parameter));
    }

    @Override
    public String getHeader(String name) {
        return stripXSS(super.getHeader(name));
    }

    private String stripXSS(String value) {
        if (value == null) {
            return null;
        }
        return value.replace(OPEN_TAG, EMPTY_STRING).replace(CLOSE_TAG, EMPTY_STRING);
    }
}
