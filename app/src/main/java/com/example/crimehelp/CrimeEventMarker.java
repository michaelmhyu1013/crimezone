package com.example.crimehelp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CrimeEventMarker {
    public static Set<String> crimeType =  new HashSet<>(Arrays.asList(
            "Break and Enter Residential/Other",
            "Homicide",
            "Mischief",
            "Offence Against a Person",
            "Other Theft",
            "Theft from Vehicle",
            "Theft of Bicycle",
            "Theft of Vehicle",
            "Vehicle Collision or Pedestrian Struck (with Fatality)",
            "Vehicle Collision or Pedestrian Struck (with Injury)"));

    private String DAY;
    private String HOUR;
    private String HUNDRED_BLOCK;
    private String TYPE;
    private String MINUTE;
    private String MONTH;
    private String X;
    private String Y;
    private String YEAR;

    public String getNEIGHBOURHOOD() {
        return NEIGHBOURHOOD;
    }

    public void setNEIGHBOURHOOD(String nEIGHBOURHOOD) {
        this.NEIGHBOURHOOD = nEIGHBOURHOOD;
    }

    private String NEIGHBOURHOOD;
    //private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getDAY() {
        return DAY;
    }

    public void setDAY(String dAY) {
        this.DAY = dAY;
    }

    public String getHOUR() {
        return HOUR;
    }

    public void setHOUR(String hOUR) {
        this.HOUR = hOUR;
    }

    public String getHUNDRED_BLOCK() {
        return HUNDRED_BLOCK;
    }

    public void setHUNDRED_BLOCK(String HUNDRED_BLOCK) {
        this.HUNDRED_BLOCK = HUNDRED_BLOCK;
    }

    public String getMINUTE() {
        return MINUTE;
    }

    public void setMINUTE(String mINUTE) {
        this.MINUTE = mINUTE;
    }

    public String getMONTH() {
        return MONTH;
    }

    public void setMONTH(String mONTH) {
        this.MONTH = mONTH;
    }

    public String getX() {
        return X;
    }

    public void setX(String x) {
        this.X = x;
    }

    public String getY() {
        return Y;
    }

    public void setY(String y) {
        this.Y = y;
    }

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String yEAR) {
        this.YEAR = yEAR;
    }


    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }
}