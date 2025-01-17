package br.com.finalcraft.atomicenergysimulator.common.util;

import java.text.DecimalFormat;

public class FCMathUtil {

    public static int floor(float value){
        return floor((double) value);
    }

    public static int floor(double value){
        int valueAsInt = (int)value;
        return value < (double) valueAsInt ? valueAsInt - 1 : valueAsInt;
    }

    public static DecimalFormat decimalFormat = new DecimalFormat("0");
    public static String toString(double value){
        if (value % 1 != 0){
            return String.valueOf(normalizeDouble(value));
        }else {
            if (value < Long.MAX_VALUE && value > -Long.MAX_VALUE){
                return String.valueOf((long) value);
            }else {
                return decimalFormat.format(value);
            }
        }
    }

    public static double normalizeDouble(double value) {
        return normalizeDouble(value, 2);
    }

    public static double normalizeDouble(double value, int zeros) {
        if (zeros < 0) throw new IllegalArgumentException("'zeros' can not be negative, passed one was: " + zeros);
        double realZeros;
        switch (zeros) {
            case 0:                realZeros = 1;                   break;
            case 1:                realZeros = 10;                  break;
            case 2:                realZeros = 100;                 break;
            case 3:                realZeros = 1000;                break;
            case 4:                realZeros = 10000;               break;
            default:               realZeros = Math.pow(10,zeros);
        }
        return ((double) Math.round(value * realZeros) / realZeros);
    }

}
