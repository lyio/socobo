package models;

public class Amount {

    public static enum UNIT {GRAM, PIECE, LITRE}
    public final int amount;

    public final UNIT unit;

    public Amount(int amount, UNIT unit) {
        this.amount = amount;
        this.unit = unit;
    }

}
