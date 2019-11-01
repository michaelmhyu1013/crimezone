package com.example.crimehelp;

import java.util.Map;

public class CrimeEventMarker {

    private long DAY;
    private long HOUR;
    private String HUNDRED_BLOCK;
    private String TYPE;
    private long MINUTE;
    private long MONTH;
    private double X;
    private double Y;
    private long YEAR;
    //private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public long getDAY() {
        return DAY;
    }

    public void setDAY(long dAY) {
        this.DAY = dAY;
    }

    public long getHOUR() {
        return HOUR;
    }

    public void setHOUR(long hOUR) {
        this.HOUR = hOUR;
    }

    public String getHUNDRED_BLOCK() {
        return HUNDRED_BLOCK;
    }

    public void setHUNDRED_BLOCK(String HUNDRED_BLOCK) {
        this.HUNDRED_BLOCK = HUNDRED_BLOCK;
    }

    public long getMINUTE() {
        return MINUTE;
    }

    public void setMINUTE(long mINUTE) {
        this.MINUTE = mINUTE;
    }

    public long getMONTH() {
        return MONTH;
    }

    public void setMONTH(long mONTH) {
        this.MONTH = mONTH;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        this.X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        this.Y = y;
    }

    public long getYEAR() {
        return YEAR;
    }

    public void setYEAR(long yEAR) {
        this.YEAR = yEAR;
    }


    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }
}