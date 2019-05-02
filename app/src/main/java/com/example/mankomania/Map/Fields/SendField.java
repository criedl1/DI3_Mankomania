package com.example.mankomania.Map.Fields;

import com.example.mankomania.GameData.GameData;

public class SendField implements Field {
    private int from;
    private int to;

    public SendField(int from, int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public void doAction(GameData gameData) {
        if (gameData.getPosition()[gameData.getHasTurn()] == from) {
            gameData.setPosition(gameData.getHasTurn(), this.to);
        }
    }

    @Override
    public void goOver(GameData gameData) {
    }
}