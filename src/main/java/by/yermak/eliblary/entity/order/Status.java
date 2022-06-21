package by.yermak.eliblary.entity.order;

public enum Status {
    ORDERED("ordered"),
    RESERVED("reserved"),
    RETURNED("returned"),
    REJECTED("rejected");

    private final String name;

    Status(String status) {
        this.name = status;
    }

    public String getName() {
        return name;
    }
}
