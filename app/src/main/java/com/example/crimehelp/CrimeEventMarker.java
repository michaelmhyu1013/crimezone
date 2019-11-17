package com.example.crimehelp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class CrimeEventMarker implements Parcelable {
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
    private String NEIGHBOURHOOD;

    protected CrimeEventMarker(Parcel in) {
        DAY = in.readString();
        HOUR = in.readString();
        HUNDRED_BLOCK = in.readString();
        TYPE = in.readString();
        MINUTE = in.readString();
        MONTH = in.readString();
        X = in.readString();
        Y = in.readString();
        YEAR = in.readString();
        NEIGHBOURHOOD = in.readString();
    }

    public static final Parcelable.Creator<CrimeEventMarker> CREATOR = new Parcelable.Creator<CrimeEventMarker>() {
        @Override
        public CrimeEventMarker createFromParcel(Parcel in) {
            return new CrimeEventMarker(in);
        }

        @Override
        public CrimeEventMarker[] newArray(int size) {
            return new CrimeEventMarker[size];
        }
    };

    public String getNEIGHBOURHOOD() {
        return NEIGHBOURHOOD;
    }

    public void setNEIGHBOURHOOD(String nEIGHBOURHOOD) {
        this.NEIGHBOURHOOD = nEIGHBOURHOOD;
    }

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

    @Override
    public String toString() {
        String tmp = "";
        try{
            tmp += getTYPE() + "~";
            tmp += getHUNDRED_BLOCK() + "~";
            tmp += getNEIGHBOURHOOD() + "~";
            tmp += new GregorianCalendar(Integer.parseInt(getYEAR()),
                    Integer.parseInt(getMONTH()), Integer.parseInt(getDAY()),
                    Integer.parseInt(getHOUR()), Integer.parseInt(getMINUTE())).getTime();
        }catch(Exception e) {
            //We do append this so that if another method splits it will have the correct number of elements
            tmp = "~~~";
            e.printStackTrace();
        }
        return tmp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(DAY);
        parcel.writeString(HOUR);
        parcel.writeString(HUNDRED_BLOCK);
        parcel.writeString(TYPE);
        parcel.writeString(MINUTE);
        parcel.writeString(MONTH);
        parcel.writeString(X);
        parcel.writeString(Y);
        parcel.writeString(YEAR);
        parcel.writeString(NEIGHBOURHOOD);

    }
}