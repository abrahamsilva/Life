package mx.itesm.edu.life.models;

import android.net.Uri;

public class Tip {

    private String title;
    private String icon;
    private String id;

    public Tip(){

    }

    public Tip(String title, String icon, String id) {

        this.title = title;
        this.icon = icon;
        this.id=id;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
