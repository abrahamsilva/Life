package mx.itesm.edu.life.models;

public class CalEvent {
    private String desc;
    private String title;
    private int day;
    private int month;
    private int year;
    private String time;

    public CalEvent() {    }

    public CalEvent(String title, String description, int day, int month, int year, String time) {
        this.desc = description;
        this.title = title;
        this.day = day;
        this.month = month;
        this.year = year;
        this.time = time;
    }

    public String getDesc() {
        return desc;
    }

    public String getTitle() {
        return title;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getTime() {
        return time;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
