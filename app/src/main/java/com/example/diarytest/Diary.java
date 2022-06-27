package com.example.diarytest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Diary implements Comparable<Diary>{
    public String wrtieTime = "";
    public String diaryText = "";

    @Override
    public int compareTo(Diary diary) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(diary.wrtieTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = formatter.parse(wrtieTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date.before(date2)){
            return -1;
        }
        else if (date.after(date2))
        {
            return 1;
        }
        return 0;
    }
}
