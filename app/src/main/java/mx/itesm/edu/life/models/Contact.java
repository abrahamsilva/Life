package mx.itesm.edu.life.models;

import java.io.Serializable;

public class Contact implements Serializable {

    private String id, name, mail, desc;

    public Contact() {
    }

    public Contact(String id, String name, String mail, String desc) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}