package by.yermak.yermak.eliblary.util.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class CopyrightTag extends TagSupport {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COPYRIGHT_TAG = "<div class = \"footer\" id=\"footer\">@Copyright 2022 eLibrary " +
            "by Renata Yermak. All rights reserved.</div>";

    @Override
    public int doStartTag() throws JspTagException {
        try {
            JspWriter out = pageContext.getOut();
            out.print(COPYRIGHT_TAG);
        } catch (IOException e) {
            LOGGER.warn(e);
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }

}

