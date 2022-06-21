package by.yermak.eliblary.entity.order;

public enum Type {
    READING_ROOM("Reading Room"),
    SEASON_TICKET("Season Ticket");

    private final String name;

    Type(String type) {
        this.name = type;
    }

    public String getName() {
        return name;
    }
}
