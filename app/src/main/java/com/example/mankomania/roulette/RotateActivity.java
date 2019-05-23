package com.example.mankomania.roulette;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mankomania.R;

public class RotateActivity extends AppCompatActivity {

    private ImageView imageView;
    private float degree;
    private String returnString;
    private ColorEnum color;
    private int randomNumber;
    private String colorString;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate);

        Intent it = getIntent();
        Bundle extras = it.getExtras();
        returnString = extras.getString("returnString");
        color = (ColorEnum) extras.get("color");
        colorString = extras.getString("colorString");
        randomNumber = extras.getInt("randomNumber");
        degree = extras.getFloat("degree");

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setText(getString(R.string.roulette_back));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) { //since this is an override method, i am not able
                //to make the return type void.

               if(event.getAction() == MotionEvent.ACTION_MOVE) {
                   if (event.getX() <= imageView.getX()) {
                       float degrees = (1080+degree)*-1;
                       rotateAnimation(degrees);
                   } else if (event.getX() > imageView.getX()) {
                       float degrees = (1080 + degree*-1);
                       rotateAnimation(degrees);
                   }
               }
                return true;
            }
        });
    }

    private void rotateAnimation(float toDegrees) {
        RotateAnimation rotate = new RotateAnimation((float) 0, (float) toDegrees,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(2000);
        rotate.setFillEnabled(true);
        rotate.setFillAfter(true);
        imageView.startAnimation(rotate);
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                openPopUp();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void openPopUp() {
        PopUp popClass = new PopUp();
        popClass.show(getSupportFragmentManager(), "alert");

        Bundle extras = new Bundle();
        extras.putString("returnString", returnString);
        extras.putInt("randomNumber", randomNumber); //toString() is not possible here
        extras.putString("color", colorString);
        popClass.setArguments(extras);
    }

    protected ColorEnum getColor() {
        return color;
    }
}