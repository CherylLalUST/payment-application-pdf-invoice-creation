package com.example.payments.util;

public class NumberToWordsConverter {

    private static final String[] units = {
            "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
            "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"
    };

    private static final String[] tens = {
            "",         // 0
            "",         // 1
            "Twenty",   // 2
            "Thirty",   // 3
            "Forty",    // 4
            "Fifty",    // 5
            "Sixty",    // 6
            "Seventy",  // 7
            "Eighty",   // 8
            "Ninety"    // 9
    };

    public static String convert(double number) {
        if (number == 0) {
            return "Zero";
        }

        if (number < 20) {
            return units[(int) number];
        }

        if (number < 100) {
            return tens[(int) (number / 10)] + (number % 10 != 0 ? " " + units[(int) (number % 10)] : "");
        }

        if (number < 1000) {
            return units[(int) (number / 100)] + " Hundred" + (number % 100 != 0 ? " and " + convert(number % 100) : "");
        }

        if (number < 1000000) {
            return convert(number / 1000) + " Thousand" + (number % 1000 != 0 ? " " + convert(number % 1000) : "");
        }

        return convert(number / 1000000) + " Million" + (number % 1000000 != 0 ? " " + convert(number % 1000000) : "");
    }
}
