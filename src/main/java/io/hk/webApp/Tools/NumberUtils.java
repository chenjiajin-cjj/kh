package io.hk.webApp.Tools;

public class NumberUtils {


    public static boolean isIntegerMinus(int ... numbers){
        for (int i = 0; i < numbers.length; i++) {
            if(numbers[i] <= 0){
                return true;
            }
        }
        return false;
    }

    public static boolean isDoubleMinus(double ... numbers){
        for (int i = 0; i < numbers.length; i++) {
            if(numbers[i] <= 0){
                return true;
            }
        }
        return false;
    }

}
