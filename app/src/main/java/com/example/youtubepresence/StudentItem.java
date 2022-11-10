package com.example.youtubepresence;

public class StudentItem {
    private long sid;
    private  String specialité_anné;
    private String name;
    private String status;
    private String absent;
    private String present;
    private String n_ligne;



    public StudentItem(long sid, String roll, String name) {
        this.sid=sid;
        this.specialité_anné = roll;
        this.name = name;
        status="";
        absent="";
        present="";

    }
    public StudentItem(long sid, String specialitéAnn, String n_ligne,String name) {
        this.sid=sid;
        this.specialité_anné = specialitéAnn;
        this.n_ligne =n_ligne;
        this.name = name;
        status="";
        absent="";
        present="";

    }


    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public String getSpecialité_anné() {
        return specialité_anné;
    }

    public void setSpecialité_anné(String specialité_anné) {
        this.specialité_anné = specialité_anné;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getN_ligne() {
        return n_ligne;
    }

    public void setN_ligne(String n_ligne) {
        this.n_ligne = n_ligne;
    }
}
