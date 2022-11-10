package com.example.youtubepresence;

public class ClassItem {
    private long cid;
    private String className;

    private String numberStudent;
    public ClassItem(long cid, String className) {
        this.cid = cid;
        this.className = className;
        numberStudent=null;
    }


    public String getNumberStudent() {
        return numberStudent;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }



    public void setNumberStudent(String numberStudent) {
        this.numberStudent = numberStudent;
    }
}
