package com.example.mankomania.gamedata;

import com.example.mankomania.network.server.ServerQueueHandler;

import java.util.Arrays;

public class GameData {
    private String[] players;
    private int[] position;
    private int[] money;
    private int lotto;
    private int[] hotels;
    private int[] infineonAktie;
    private int[] hypoAktie;
    private int[] strabagAktie;
    private int[] sandwirthHotel;
    private int[] plattenwirtHotel;
    private int[] seeparkHotel;
    private boolean[] isCheater;
    private int hasTurn = 0;
    private ServerQueueHandler server;

    public String[] getPlayers() {
        return players.clone();
    }
    public void setPlayers(String[] player) {
        players = player;
    }

    public int[] getPosition() {
        return position.clone();
    }
    public void setPosition(int[] position) {
        this.position = position;
    }

    //sets position of specific player and sends changes to all clients
    public void setPosition(int player, int position){
        this.position[player] = position;
    }

    public int[] getMoney() {
        return money.clone();
    }
    public void setMoney(int[] money) {
        this.money = money;
    }

    //sets money of a player and sends change to all clients
    public void setMoney(int player, int money){
        this.getMoney()[player] = money;
        if(this.server != null) {
            this.server.sendMoney(player, money);
        }
    }

    public int getPlayerCount(){
        int count = 0;
        for (String player : this.players) {
            if(player != null){
                count++;
            }
        }
        return count;
    }

    public int getLotto() {
        return lotto;
    }

    //sets lotto money and sends changes to all Clients
    public void setLotto(int lotto) {
        this.lotto = lotto;
        if(this.server != null){
            this.server.sendLotto(lotto);
        }
    }

    public int[] getHotels() {
        return hotels.clone();
    }
    public void setHotels(int[] hotels) {
        this.hotels = hotels;
    }

    public int[] getInfineonAktie() {
        return infineonAktie.clone();
    }
    public void setInfineonAktie(int[] infineonAktie) {
        this.infineonAktie = infineonAktie;
    }

    public int[] getHypoAktie() {
        return hypoAktie.clone();
    }

    public void setHypoAktie(int[] hypoAktie) {
        this.hypoAktie = hypoAktie;
    }

    public int[] getStrabagAktie() {
        return strabagAktie.clone();
    }
    public void setStrabagAktie(int[] strabagAktie) {
        this.strabagAktie = strabagAktie;
    }

    public int[] getSandwirthHotel() {
        return sandwirthHotel.clone();
    }
    public void setSandwirthHotel(int[] sandwirthHotel) {
        this.sandwirthHotel = sandwirthHotel;
    }
    public int[] getPlattenwirtHotel() {
        return plattenwirtHotel.clone();
    }
    public void setPlattenwirtHotel(int[] plattenwirtHotel) {
        this.plattenwirtHotel = plattenwirtHotel;
    }
    public int[] getSeeparkHotel() {
        return seeparkHotel.clone();
    }
    public void setSeeparkHotel(int[] seeparkHotel) {
        this.seeparkHotel = seeparkHotel;
    }

    public boolean[] getIsCheater() {
        return isCheater.clone();
    }
    public void setIsCheater(boolean[] isCheater) {
        this.isCheater = isCheater;
    }

    public void setTurn(int player) {
        this.hasTurn = player;
    }

    public int getHasTurn() {
        return hasTurn;
    }

    public void setServer(ServerQueueHandler serverQueueHandler) {
        this.server = serverQueueHandler;
    }

    public void initEmptyGameData(int playerCount){
        int[] intArr = new int[playerCount];
        boolean[] boolArr = new boolean[playerCount];
        String[] strArr = new String[playerCount];

        // Set Player[] (fills in ConnectPlayers)
        Arrays.fill(strArr,"");
        setPlayers(strArr);

        // Set Arrays with 0
        Arrays.fill(intArr,0);
        setMoney(intArr);
        setPosition(intArr);
        setHypoAktie(intArr);
        setStrabagAktie(intArr);
        setInfineonAktie(intArr);

        // Set Array with false
        Arrays.fill(boolArr,false);
        setIsCheater(boolArr);

        // Set Lotto to 0
        setLotto(0);

        // Set all Hotel to 0
        intArr = new int[5];
        Arrays.fill(intArr,0);
        setHotels(intArr);
    }
}
