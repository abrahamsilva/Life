package mx.itesm.edu.life.models;

public class Tip {

    private String title;
    private String icon;
    private String id;
    private String desc;

    public Tip(){    }

    public Tip(String title, String icon, String id, String desc) {

        this.title = title;
        this.icon = icon;
        this.id = id;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
