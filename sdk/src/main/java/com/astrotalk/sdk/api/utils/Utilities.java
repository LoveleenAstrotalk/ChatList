package com.astrotalk.sdk.api.utils;

import android.content.SharedPreferences;
import android.text.Html;
import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utilities {

    public static String getConvertedValueFromINR(double valueInINR, SharedPreferences sharedPreferences) {
        String currencySymbol = getCurrencySymbol2(sharedPreferences);
        if (valueInINR == 0) {
            return currencySymbol + " " + 0 + "";
        }
        try {
            int conversionFactor = sharedPreferences.getInt(Constants.CONVERSION_FACTOR, 1);
            BigDecimal a = new BigDecimal(valueInINR / conversionFactor);
            BigDecimal roundOff = a.setScale(2, RoundingMode.CEILING);
            return currencySymbol + " " + roundOff.stripTrailingZeros().toPlainString() + "";
        } catch (Exception ex) {
            return currencySymbol + " " + 0 + "";
        }
    }

    public static String convertFromINR(double valueInINR, SharedPreferences sharedPreferences) {
        if (valueInINR == 0) {
            return String.valueOf(0);
        }
        try {
            int conversionFactor = sharedPreferences.getInt(Constants.CONVERSION_FACTOR, 1);
            Log.d("TAG", conversionFactor + " " + valueInINR);
            BigDecimal a = new BigDecimal(valueInINR / conversionFactor);
            BigDecimal roundOff = a.setScale(2, RoundingMode.CEILING);

            return roundOff.stripTrailingZeros().toPlainString();
        } catch (Exception ex) {
            return String.valueOf(0);
        }

    }

    public static String getCurrencySymbol2(SharedPreferences sharedPreferences) {
        String symbol = sharedPreferences.getString(Constants.ISO_CODE, "INR");
        if (symbol.equalsIgnoreCase("INR")) {
            symbol = "₹";
        }
        return String.valueOf(Html.fromHtml(symbol));
    }

}
