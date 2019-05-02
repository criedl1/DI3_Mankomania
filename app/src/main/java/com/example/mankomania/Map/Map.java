package com.example.mankomania.Map;

import com.example.mankomania.Map.Fields.Field;
import com.example.mankomania.Map.Fields.LossField;
import com.example.mankomania.Map.Fields.LottoField;
import com.example.mankomania.Map.Fields.SendField;

import java.util.ArrayList;

public class Map {
    private final ArrayList<Field> fields = new ArrayList<>();

    public Map() {
        super();
        this.populateMap();
    }

    private void populateMap(){
        fields.add(new SendField(0,2));
        fields.add(new LossField( 10000));
        fields.add(new LottoField(5000,50000));
        fields.add(new LossField(30000));
        fields.add(new SendField(4,1));
    }

    public Field getField(int position){
        return this.fields.get(position);
    }

    public int getMapSize(){
        return this.fields.size();
    }


}