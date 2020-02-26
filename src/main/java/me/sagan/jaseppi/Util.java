package me.sagan.jaseppi;

public class Util {

    public static boolean allEqual(String[] arr, String val) {
        boolean equal = true;
        for (String s : arr) {
            if (!s.equalsIgnoreCase(val)) {
                equal = false;
                break;
            }
        }
        return equal;
    }

    public static boolean allEqual(String[][] arr, String val) {
        boolean equal = true;
        for (String[] s : arr) {
            if (!allEqual(s, val)) {
                equal = false;
                break;
            }
        }
        return equal;
    }
}