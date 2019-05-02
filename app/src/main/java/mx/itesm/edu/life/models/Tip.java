package mx.itesm.edu.life.models;

public class Tip {

    private String title, desc;
    private int icon;

    public Tip(String title, int icon, String desc) {

        this.title = title;
        this.icon = icon;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
