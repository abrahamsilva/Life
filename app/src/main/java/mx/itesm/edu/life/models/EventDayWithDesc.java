package mx.itesm.edu.life.models;

import android.support.annotation.DrawableRes;

import com.applandeo.materialcalendarview.EventDay;

import java.util.Calendar;

public class EventDayWithDesc extends EventDay {
    private String mDescription;
    private String mTitle;

    public EventDayWithDesc(Calendar day, @DrawableRes int drawable, String title, String description){
        super(day, drawable);
        this.mDescription = description;
        this.mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getTitle() {
        return mTitle;
    }
}
