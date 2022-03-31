package com.astrotalk.sdk.api.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

    private static SimpleDateFormat simpleDateFormatNew = new SimpleDateFormat("dd MMM yyyy");

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

    public static String getCurrencySymbol2(SharedPreferences sharedPreferences) {
        String symbol = sharedPreferences.getString(Constants.ISO_CODE, "INR");
        if (symbol.equalsIgnoreCase("INR")) {
            symbol = "₹";
        }
        return String.valueOf(Html.fromHtml(symbol));
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static String longToDateWithoutHyphen(long timeInMillis) {
        Date date = new Date(timeInMillis);
        return simpleDateFormatNew.format(date);
    }
}
