package com.bidzis.quizandroid.ranking.Punkty;

/**
 * Created by Bidzis on 1/18/2017.
 */

public class PunktyClass {
    private int id;
    private String uzytkownik;
    private int punkty;
    private int miejsce;

    public PunktyClass(int id, String uzytkownik, int punkty, int miejsce) {
        this.id = id;
        this.uzytkownik = uzytkownik;
        this.punkty = punkty;
        this.miejsce = miejsce;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(String uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public int getPunkty() {
        return punkty;
    }

    public void setPunkty(int punkty) {
        this.punkty = punkty;
    }

    public int getMiejsce() {
        return miejsce;
    }

    public void setMiejsce(int miejsce) {
        this.miejsce = miejsce;
    }
}
