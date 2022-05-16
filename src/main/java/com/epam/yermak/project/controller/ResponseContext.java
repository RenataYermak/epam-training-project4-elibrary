package com.epam.yermak.project.controller;

public class ResponseContext {
    public enum ResponseContextType {
        FORWARD,
        REDIRECT
    }
    private final String pagePath;
    private final ResponseContextType responseContextType;

    public ResponseContext(String pagePath, ResponseContextType responseContextType) {
        this.pagePath = pagePath;
        this.responseContextType = responseContextType;
    }

    public String getPagePath() {
        return pagePath;
    }

    public ResponseContextType getResponseContextType() {
        return responseContextType;
    }
}
