package net.amik.createarsenal.util;

@SuppressWarnings("unused")
public class IntUtil {
    public static int sign(double value) {
        return value > 0 ? 1 : (value < 0 ? -1 : 0);
    }

    public static int isPositive(double value) {
        return value > 0 ? 1 : 0;
    }

    public static int isNegative(double value) {
        return value < 0 ? 1 : 0;
    }

    public static int toInt(boolean value) {
        return value ? 1 : 0;
    }

    public static int posOrNeg(boolean value) {
        return value ? 1 : -1;
    }
}
