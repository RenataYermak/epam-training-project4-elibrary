package by.yermak.yermak.eliblary.model.book;

public enum Category {
    SCI_FI("sci_fi"),
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
