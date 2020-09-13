package com.optisystems.interview.test.utils;

import java.math.BigInteger;
import java.util.Comparator;

@SuppressWarnings("WeakerAccess")
public class StringComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        int value = o1.replace(" ", "").compareToIgnoreCase(o2.replace(" ", ""));
        if (identicalFirstCharacter(o1, o2)) {
            if (Character.isLowerCase(o1.charAt(0)) && Character.isUpperCase(o2.charAt(0))){
                value = Math.abs(value);
            } else if (Character.isUpperCase(o1.charAt(0)) && Character.isLowerCase(o2.charAt(0))) {
                value = -Math.abs(value);
            }
        }
        if (value != 0 && (notContainsInt(o1) || notContainsInt(o2))) return value;
        return extractInt(o1).compareTo(extractInt(o2));
    }

    private boolean identicalFirstCharacter(String o1, String o2) {
        return Character.toLowerCase(o1.charAt(0)) == Character.toLowerCase(o2.charAt(0));
    }

    BigInteger extractInt(String s) {
        String num = s.replaceAll("\\D", "");
        return num.isEmpty() ? BigInteger.ZERO : new BigInteger(num);
    }

    boolean notContainsInt(String s) {
        return ! Character.isDigit(s.charAt(0));
    }
}
