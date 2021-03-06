package com.example.mankomania.map;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Locale;

public class Player implements Serializable {

    private ImageView figure;


    private int temporaryField;
    private int currentField;
    private int money = 0;
    private TextView currentmoney;
    private int[] aktien = new int[3];
    private boolean didCheat = false;
    private boolean didBlame = false;

    public Player() {
        currentField = 0;
        temporaryField = 0;
        Arrays.fill(aktien, 0);
    }

    public void initFields(ImageView figure, TextView currentmoney) {
        this.figure = figure;
        this.currentmoney = currentmoney;
        currentmoney.setVisibility(View.VISIBLE);
        this.updateMoneyField();
    }

    public int getTemporaryField() {
        return temporaryField;
    }

    public void setTemporaryField(int temporaryField) {
        this.temporaryField = temporaryField;
    }

    ImageView getFigure() {
        return figure;
    }

    public int getCurrentField() {
        return currentField;
    }


    public int getMoney() {
        return money;
    }

    public int setMoney(int money) {
        this.money = money;
        this.updateMoneyField();
        return money;
    }

    private void updateMoneyField() {
        this.currentmoney.setText(String.format(Locale.GERMAN, "%d", this.money));
    }


    public void setPosition(int position) {
        this.currentField = position;
    }



    void setAktie(Aktien aktien, int count) {
        switch (aktien) {
            case HYPO:
                this.aktien[0] = count;
                break;
            case STRABAG:
                this.aktien[1] = count;
                break;
            case INFINEON:
                this.aktien[2] = count;
                break;
            default:
                throw new IllegalStateException("Aktie does not exist");
        }
    }

    public int[] getAktien() {
        return aktien;
    }

    void setIP(String ip) {
        // maybe needed in future
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Player) {
            Player p = (Player) o;
            if (p.getCurrentField() == currentField && p.getTemporaryField() == temporaryField && p.getMoney() == money) {
                for (int i = 0; i < aktien.length; i++) {
                    if (p.getAktien()[i] != aktien[i]) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    void initMyMoneyField(int color) {
        this.currentmoney.setBackgroundColor(color);
    }

    boolean isDidCheat() {
        return didCheat;
    }

    void setDidCheat(boolean didCheat) {
        this.didCheat = didCheat;
    }

    boolean isDidBlame() {
        return didBlame;
    }

    void setDidBlame(boolean didBlame) {
        this.didBlame = didBlame;
    }
}

