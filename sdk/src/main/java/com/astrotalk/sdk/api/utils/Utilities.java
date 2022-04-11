package com.astrotalk.sdk.api.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.Display;
import android.view.Window;
import android.widget.Toast;

import com.astrotalk.sdk.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

    static Dialog dialogLoader;

    public static void showLoader(Context context) {
        dialogLoader = new Dialog(context);
        dialogLoader.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLoader.setCancelable(false);
        dialogLoader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogLoader.setContentView(R.layout.dialog_loader);

        if(!dialogLoader.isShowing()) {
            dialogLoader.show();
        }
    }

    public static void closeLoader() {
        if (dialogLoader.isShowing()) {
            dialogLoader.cancel();
        }
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static int getScreenHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        int newHeight = height / 2;
        return newHeight;
    }


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

    public static String longToDateWithoutHyphen(long timeInMillis) {
        Date date = new Date(timeInMillis);
        return simpleDateFormatNew.format(date);
    }
}
