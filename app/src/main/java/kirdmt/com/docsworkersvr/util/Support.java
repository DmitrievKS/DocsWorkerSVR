package kirdmt.com.docsworkersvr.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Support {

    public String stringCutter(String str, int borderIndex) {

        String returnedString = null;

        if (str.length() > borderIndex && borderIndex > 0) {
            returnedString = str.substring(0, borderIndex - 1) + "...";
        } else {
            returnedString = str;
        }


        return returnedString;
    }


     public String getCurrentMoscowTimeAndDate()
    {
        SimpleDateFormat moscowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        moscowTime.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return moscowTime.format(new Date());
    }

}
