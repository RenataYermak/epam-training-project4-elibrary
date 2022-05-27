package by.yermak.eliblary.entity.order;

public enum Issue {
    READING_ROOM("Reading Room"),
    SEASON_TICKET("Season Ticket");

    private final String value;

    Issue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
