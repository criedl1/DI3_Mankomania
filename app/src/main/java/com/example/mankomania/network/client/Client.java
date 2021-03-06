package com.example.mankomania.network.client;

import android.util.Log;

import com.example.mankomania.gamedata.GameData;
import com.example.mankomania.map.MapView;
import com.example.mankomania.network.NetworkConstants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

// Client class
public class Client extends Thread {
    private static String name;
    private final GameData gameData = new GameData();
    private static String ipHost;
    private PrintWriter output;
    private int idx;

    public static MapView getMapView() {
        return mapView;
    }

    private static MapView mapView;

    public Client(){
        // just for sonarCloud
    }

    public static void init(String ipHost, MapView mapView, String name){
        Client.ipHost = ipHost;
        Client.mapView = mapView;
        Client.name = name;
    }

    @Override
    public void run() {
        try(// establish the connection with server port 5056
            Socket socket = new Socket(InetAddress.getByName(ipHost), 5056))
        {

            // obtaining INPUT and out
            output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            Queue<String> queue = new LinkedBlockingQueue<>();

            // start ClientListener for incoming Messages
            ClientListener clientListener = new ClientListener(input, queue);
            clientListener.start();

            // start ClientQueueHandler
            ClientQueueHandler clientQueueHandler = new ClientQueueHandler(queue, this, gameData);
            clientQueueHandler.start();

            //Close Socket
            clientListener.join();
            clientQueueHandler.join();

        } catch (Exception err) {
            Log.e("CLIENT", ""+ err);
        }
    }

    public void setMyName() {
        // new Thread because Network cant be on the UI Thread (temp Fix)
        Thread thread = new Thread(){
            @Override
            public void run(){
                JsonObject json = new JsonObject();
                json.addProperty(NetworkConstants.OPERATION,NetworkConstants.SET_NAME);
                json.addProperty(NetworkConstants.NAME, name);
                json.addProperty(NetworkConstants.PLAYER, idx);
                output.println(json.toString());
            }
        };
        thread.start();
    }

    //Index
    void setIdx(int idx) {
        this.idx = idx;
    }

    //Server Requests
    public void setMoneyOnServer(final int idx, final int money){
        // new Thread because Network cant be on the UI Thread (temp Fix)
        Thread thread = new Thread(){
            @Override
            public void run(){
                JsonObject json = new JsonObject();
                json.addProperty(NetworkConstants.OPERATION,NetworkConstants.SEND_MONEY);
                json.addProperty(NetworkConstants.PLAYER, idx);
                json.addProperty(NetworkConstants.MONEY, money);
                output.println(json.toString());
            }
        };
        thread.start();
    }

    public void setHypoAktieOnServer(final int idx,final int count){
        // new Thread because Network cant be on the UI Thread (temp Fix)
        Thread thread = new Thread(){
            @Override
            public void run(){
                JsonObject json = new JsonObject();
                json.addProperty(NetworkConstants.OPERATION,NetworkConstants.SET_HYPO_AKTIE);
                json.addProperty(NetworkConstants.PLAYER, idx);
                json.addProperty(NetworkConstants.COUNT, count);
                output.println(json.toString());
            }
        };
        thread.start();
    }
    public void setStrabagAktieOnServer(final int idx,final int count){
        // new Thread because Network cant be on the UI Thread (temp Fix)
        Thread thread = new Thread(){
            @Override
            public void run(){
                JsonObject json = new JsonObject();
                json.addProperty(NetworkConstants.OPERATION,NetworkConstants.SET_STRABAG_AKTIE);
                json.addProperty(NetworkConstants.PLAYER, idx);
                json.addProperty(NetworkConstants.COUNT, count);
                output.println(json.toString());
            }
        };
        thread.start();
    }
    public void setInfineonAktieOnServer(final int idx, final int count){
        // new Thread because Network cant be on the UI Thread (temp Fix)
        Thread thread = new Thread(){
            @Override
            public void run(){
                JsonObject json = new JsonObject();
                json.addProperty(NetworkConstants.OPERATION,NetworkConstants.SET_INFINEON_AKTIE);
                json.addProperty(NetworkConstants.PLAYER, idx);
                json.addProperty(NetworkConstants.COUNT, count);
                output.println(json.toString());
            }
        };
        thread.start();
    }
    public void setMeAsCheater(){
        // new Thread because Network cant be on the UI Thread (temp Fix)
        Thread thread = new Thread(){
            @Override
            public void run(){
                JsonObject json = new JsonObject();
                json.addProperty(NetworkConstants.OPERATION,NetworkConstants.SET_CHEATER);
                json.addProperty(NetworkConstants.PLAYER, idx);
                output.println(json.toString());
            }
        };
        thread.start();
    }
    public void setLottoOnServer(final int amount){
        // new Thread because Network cant be on the UI Thread (temp Fix)
        Thread thread = new Thread(){
            @Override
            public void run(){
                JsonObject json = new JsonObject();
                json.addProperty(NetworkConstants.OPERATION,NetworkConstants.SET_LOTTO);
                json.addProperty(NetworkConstants.AMOUNT, amount);
                output.println(json.toString());
            }
        };
        thread.start();
    }
    public void setHotelOnServer(final int idx, final int owner, final int price){
        // new Thread because Network cant be on the UI Thread (temp Fix)
        Thread thread = new Thread(){
            @Override
            public void run(){
                JsonObject json = new JsonObject();
                json.addProperty(NetworkConstants.OPERATION,NetworkConstants.SET_HOTEL);
                json.addProperty(NetworkConstants.HOTEL, idx);
                json.addProperty(NetworkConstants.OWNER, owner);
                json.addProperty(NetworkConstants.HOTEL_PRICE, price);
                output.println(json.toString());
            }
        };
        thread.start();
    }


    public void rollTheDice() {
        // new Thread because Network cant be on the UI Thread (temp Fix)
        Thread thread = new Thread(){
            @Override
            public void run(){
                JsonObject json = new JsonObject();
                json.addProperty(NetworkConstants.OPERATION,NetworkConstants.ROLL_DICE);
                json.addProperty(NetworkConstants.PLAYER,idx);
                output.println(json.toString());
            }
        };
        thread.start();
    }


    public void sendCasinoResult(final int result){
        // new Thread because Network cant be on the UI Thread (temp Fix)
        Thread thread = new Thread(){
            @Override
            public void run(){
                JsonObject json = new JsonObject();
                json.addProperty(NetworkConstants.OPERATION,NetworkConstants.SEND_CASINO);
                json.addProperty(NetworkConstants.RESULT, result);
                output.println(json.toString());
            }
        };
        thread.start();
    }

    public void endTurn(){
        // new Thread because Network cant be on the UI Thread (temp Fix)
        Thread thread = new Thread(){
            @Override
            public void run(){
                JsonObject json = new JsonObject();
                json.addProperty(NetworkConstants.OPERATION,NetworkConstants.END_TURN);
                json.addProperty(NetworkConstants.PLAYER,idx);
                output.println(json.toString());
            }
        };
        thread.start();
    }

    //GameDate Requests
    public String getOwnIP(){
        return gameData.getIpAdresses()[idx];
    }
    public String[] getPlayers() {
        return gameData.getIpAdresses();
    }
    public int[] getPosition() {
        return gameData.getPosition();
    }
    public int[] getMoney() {
        return gameData.getMoney();
    }
    public int getLotto() {
        return gameData.getLotto();
    }
    public int[] getHotels() {
        return gameData.getHotels();
    }
    public int[] getInfineonAktie() {
        return gameData.getInfineonAktie();
    }
    public int[] getHypoAktie() {
        return gameData.getHypoAktie();
    }
    public int[] getStrabagAktie() {
        return gameData.getStrabagAktie();
    }

    public void sendBlame(final int cheater) {
        Thread thread = new Thread(){
            @Override
            public void run(){
                JsonObject json = new JsonObject();
                json.addProperty(NetworkConstants.OPERATION,NetworkConstants.BLAME_CHEATER);
                json.addProperty(NetworkConstants.PLAYER,idx);
                json.addProperty(NetworkConstants.CHEATER,cheater);
                output.println(json.toString());
            }
        };
        thread.start();
    }

    public void amServer() {
        Thread thread = new Thread(){
            @Override
            public void run(){
                JsonObject json = new JsonObject();
                json.addProperty(NetworkConstants.OPERATION,NetworkConstants.AMSERVER);
                json.addProperty(NetworkConstants.PLAYER,idx);
                output.println(json.toString());
            }
        };
        thread.start();
    }

    public void sendOrder(final int[] pOrder) {
        Thread thread = new Thread(){
            @Override
            public void run(){
                JsonObject json = new JsonObject();
                json.addProperty(NetworkConstants.OPERATION,NetworkConstants.SET_ORDER);
                JsonArray orderArr = new JsonArray();
                for (int order : pOrder) {
                    orderArr.add(order);
                }
                json.add(NetworkConstants.ORDER, orderArr);
                json.addProperty(NetworkConstants.PLAYER,idx);
                output.println(json.toString());
            }
        };
        thread.start();
    }
}
