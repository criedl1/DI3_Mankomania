package com.example.mankomania.slotmachine;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mankomania.R;

public class CasinoStartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casino_start_screen);

        Button start = findViewById(R.id.btnStart);
        start.setBackgroundColor(Color.TRANSPARENT);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSlotMachine();
            }
        });
    }

    private void startSlotMachine(){
        Intent it = new Intent(this, SlotMachineActivity.class);
        startActivity(it);
        finish();
    }
}
