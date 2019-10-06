package bpc.dis.recyclerview;

public enum DisTableOrientation {

    HORIZONTAL(0),
    VERTICAL(1),
    GRID(2);

    private int value;

    DisTableOrientation(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}