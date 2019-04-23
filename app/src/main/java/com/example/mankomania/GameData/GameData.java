package com.example.mankomania.GameData;

public class GameData {
    private String[] Players;
    private int[] Position;
    private int[] Money;
    private int Lotto;
    private int[] hotels;
    private int[] infineonAktie;
    private int[] hypoAktie;
    private int[] strabagAktie;
    private boolean[] isCheater;

    public String[] getPlayers() {
        return Players;
    }
    public void setPlayers(String[] player) {
        Players = player;
    }

    public int[] getPosition() {
        return Position;
    }
    public void setPosition(int[] position) {
        Position = position;
    }

    public int[] getMoney() {
        return Money;
    }
    public void setMoney(int[] money) {
        Money = money;
    }

    public int getLotto() {
        return Lotto;
    }
    public void setLotto(int lotto) {
        Lotto = lotto;
    }

    public int[] getHotels() {
        return hotels;
    }
    public void setHotels(int[] hotels) {
        this.hotels = hotels;
    }

    public int[] getInfineonAktie() {
        return infineonAktie;
    }
    public void setInfineonAktie(int[] infineonAktie) {
        this.infineonAktie = infineonAktie;
    }

    public int[] getHypoAktie() {
        return hypoAktie;
    }
    public void setHypoAktie(int[] hypoAktie) {
        this.hypoAktie = hypoAktie;
    }
    public int[] getStrabagAktie() {
        return strabagAktie;
    }
    public void setStrabagAktie(int[] strabagAktie) {
        this.strabagAktie = strabagAktie;
    }

    public boolean[] getIsCheater() {
        return isCheater;
    }
    public void setIsCheater(boolean[] isCheater) {
        this.isCheater = isCheater;
    }
}
