package by.yermak.eliblary.entity.order;

public enum Status {
    ORDERED("ordered"),
    RESERVED("reserved"),
    RETURNED("returned"),
    REJECTED("rejected");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
