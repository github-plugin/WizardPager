package widzardlib.models.enums;

public enum CallBackType {
    Skip(0),
    First(1),
    Previous(2),
    Next(3),
    Last(4);

    CallBackType(int i) {
        this.type = i;
    }

    private int type;

    public int getValue() {
        return type;
    }
}