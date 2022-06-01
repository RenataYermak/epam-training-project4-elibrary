package by.yermak.eliblary.entity.order;

public enum Type {
    READING_ROOM("Reading Room"),
    SEASON_TICKET("Season Ticket");

    private final String value;

    Type(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
