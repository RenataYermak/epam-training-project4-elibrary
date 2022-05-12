package com.epam.project.model.book;

public enum Category {
    SCI_FI("sci-fi"),
    DETECTIVE("detective"),
    NOVEL("novel");

    private final String name;

    Category(String category) {
        this.name = category;
    }

    public String getName() {
        return name;
    }
}
