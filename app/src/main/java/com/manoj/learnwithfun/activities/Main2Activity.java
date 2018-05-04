package com.manoj.learnwithfun.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.manoj.learnwithfun.R;

public class Main2Activity extends AppCompatActivity {

    Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        playButton = findViewById(R.id.playButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public static String[][] imageArray = new String[][]{
            {"https://cdn.pixabay.com/photo/2018/03/15/22/40/fraud-3229757_640.jpg",
                    "https://cdn.pixabay.com/photo/2016/11/19/14/00/code-1839406_640.jpg",
                    "https://cdn.pixabay.com/photo/2012/02/29/16/02/bar-19343_640.jpg",
                    "https://cdn.pixabay.com/photo/2013/06/03/02/24/soldier-115842_640.jpg"},

    };

    public static int[][] imageList = new int[][]{
            {R.drawable.mouse0, R.drawable.mouse1, R.drawable.mouse2, R.drawable.mouse3},
            {R.drawable.animal0, R.drawable.animal1, R.drawable.animal2, R.drawable.animal3},
            {R.drawable.parent0, R.drawable.parent1, R.drawable.parent2, R.drawable.parent3},
            {R.drawable.toy0, R.drawable.toy1, R.drawable.toy2, R.drawable.toy3},
            {R.drawable.mix0, R.drawable.mix1, R.drawable.mix2, R.drawable.mix3},
            {R.drawable.fan0, R.drawable.fan1, R.drawable.fan2, R.drawable.fan3},
            {R.drawable.smile0, R.drawable.smile1, R.drawable.smile2, R.drawable.smile3},
            {R.drawable.alarm0, R.drawable.alarm1, R.drawable.alarm2, R.drawable.alarm3},
            {R.drawable.statue0, R.drawable.statue1, R.drawable.statue2, R.drawable.statue3},
            {R.drawable.sport0, R.drawable.sport1, R.drawable.sport2, R.drawable.sport3},
            {R.drawable.crane0, R.drawable.crane1, R.drawable.crane2, R.drawable.crane3},
            {R.drawable.mask0, R.drawable.mask1, R.drawable.mask2, R.drawable.mask3},
            {R.drawable.brush0, R.drawable.brush1, R.drawable.brush2, R.drawable.brush3},
            {R.drawable.couple0, R.drawable.couple1, R.drawable.couple2, R.drawable.couple3},
            {R.drawable.hot0, R.drawable.hot1, R.drawable.hot2, R.drawable.hot3},
            {R.drawable.drink0, R.drawable.drink1, R.drawable.drink2, R.drawable.drink3},
            {R.drawable.sign0, R.drawable.sign1, R.drawable.sign2, R.drawable.sign3},
            {R.drawable.dish0, R.drawable.dish1, R.drawable.dish2, R.drawable.dish3},
            {R.drawable.rich0, R.drawable.rich1, R.drawable.rich2, R.drawable.rich3},
            {R.drawable.easter0, R.drawable.easter1, R.drawable.easter2, R.drawable.easter3},
            {R.drawable.train0, R.drawable.train1, R.drawable.train2, R.drawable.train3},
            {R.drawable.jump0, R.drawable.jump1, R.drawable.jump2, R.drawable.jump3},
            {R.drawable.children0, R.drawable.children1, R.drawable.children2, R.drawable.children3},
            {R.drawable.wave0, R.drawable.wave1, R.drawable.wave2, R.drawable.wave3},
            {R.drawable.pair0, R.drawable.pair1, R.drawable.pair2, R.drawable.pair3},
            {R.drawable.blue0, R.drawable.blue1, R.drawable.blue2, R.drawable.blue3},
            {R.drawable.round0, R.drawable.round1, R.drawable.round2, R.drawable.round3},
            {R.drawable.play0, R.drawable.play1, R.drawable.play2, R.drawable.play3},
            {R.drawable.date0, R.drawable.date1, R.drawable.date2, R.drawable.date3},
            {R.drawable.green0, R.drawable.green1, R.drawable.green2, R.drawable.green3}
    };

    public static String[] ansList = new String[]{
            "MOUSE",
            "ANIMAL",
            "PARENT",
            "TOY",
            "MIX",
            "FAN",
            "SMILE",
            "ALARM",
            "STATUE",
            "SPORT",
            "CRANE",
            "MASK",
            "BRUSH",
            "COUPLE",
            "HOT",
            "DRINK",
            "SIGN",
            "DISH",
            "RICH",
            "EASTER",
            "TRAIN",
            "JUMP",
            "CHILDREN",
            "WAVE",
            "PAIR",
            "BLUE",
            "ROUND",
            "PLAY",
            "DATE",
            "GREEN",
            "CODE"
    };

    public static void watchVideo(View view) {
    }
}
