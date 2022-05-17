package com.astrotalk.sdk.api.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.astrotalk.sdk.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AstroUtilities {

    static Dialog dialogLoader;

    public static void showLoader(Context context) {
        dialogLoader = new Dialog(context);
        dialogLoader.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLoader.setCancelable(false);
        dialogLoader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogLoader.setContentView(R.layout.at_dialog_loader);

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

    public static CharSequence trim(CharSequence s) {
        int start = 0;
        int end = s.length();
        while (start < end && Character.isWhitespace(s.charAt(start))) {
            start++;
        }
        while (end > start && Character.isWhitespace(s.charAt(end - 1))) {
            end--;
        }
        return s.subSequence(start, end);
    }

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat simpleDateFormatNew = new SimpleDateFormat("dd MMM yyyy");

    public static String getConvertedValueFromINR(double valueInINR, SharedPreferences sharedPreferences) {
        String currencySymbol = getCurrencySymbol2(sharedPreferences);
        if (valueInINR == 0) {
            return currencySymbol + " " + 0 + "";
        }
        try {
            int conversionFactor = sharedPreferences.getInt(AstroConstants.CONVERSION_FACTOR, 1);
            BigDecimal a = new BigDecimal(valueInINR / conversionFactor);
            BigDecimal roundOff = a.setScale(2, RoundingMode.CEILING);
            return currencySymbol + " " + roundOff.stripTrailingZeros().toPlainString() + "";
        } catch (Exception ex) {
            return currencySymbol + " " + 0 + "";
        }
    }

    public static String getCurrencySymbol2(SharedPreferences sharedPreferences) {
        String symbol = sharedPreferences.getString(AstroConstants.ISO_CODE, "INR");
        if (symbol.equalsIgnoreCase("INR")) {
            symbol = "₹";
        }
        return String.valueOf(Html.fromHtml(symbol));
    }

    public static String longToDateWithoutHyphen(long timeInMillis) {
        Date date = new Date(timeInMillis);
        return simpleDateFormatNew.format(date);
    }

    public static File bitmapToFile(Context context, String filename, Bitmap bitmap, boolean compress) {
        File f = new File(context.getCacheDir(), filename);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        if (compress) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 45, bos);
        } else {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        }
        byte[] bitmapData = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    public static void preventMultiClick(final View view) {
        if (!view.isEnabled()) {
            return;
        }
        view.setEnabled(false);
        view.postDelayed(() -> view.setEnabled(true), 500);
    }
}
