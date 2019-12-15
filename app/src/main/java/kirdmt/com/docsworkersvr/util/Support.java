package kirdmt.com.docsworkersvr.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public final class Support {

    private static final String SUPPORT_TAG = "SupportTAG";

    public static String stringCutter(String str, int borderIndex) {

        String returnedString = null;

        if (str.length() > borderIndex && borderIndex > 0) {
            returnedString = str.substring(0, borderIndex - 1) + "...";
        } else {
            returnedString = str;
        }


        return returnedString;
    }


    public static String getCurrentMoscowTimeAndDate() {
        SimpleDateFormat moscowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        moscowTime.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return moscowTime.format(new Date());
    }

    public static int getHouseIndex(ArrayList<String> housesList, String houseName) {

        int houseIndex = 0;

        try {

            if (!housesList.isEmpty()) {
                houseIndex = housesList.indexOf(houseName);
            }
        } catch (NullPointerException e) {
            Log.d(SUPPORT_TAG, "NullPointException is : " + e.getMessage());
        }

        return houseIndex;
    }

}
