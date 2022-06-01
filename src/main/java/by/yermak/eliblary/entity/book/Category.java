package by.yermak.eliblary.entity.book;

public enum Category {
    SCI_FI("sci_fi"),
    DETECTIVE("detective"),
    NOVEL("novel"),
    SCIENCE("science");

    private final String name;

    Category(String category) {
        this.name = category;
    }

    public String getName() {
        return name;
    }
}
