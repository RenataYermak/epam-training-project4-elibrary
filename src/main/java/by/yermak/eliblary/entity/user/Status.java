package by.yermak.eliblary.entity.user;

public enum Status {
    ACTIVATED("activated"),
    DEACTIVATED("deactivated");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
