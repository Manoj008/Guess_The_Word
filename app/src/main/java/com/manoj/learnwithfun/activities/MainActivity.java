package com.manoj.learnwithfun.activities;

import android.app.Dialog;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.manoj.learnwithfun.R;
import com.manoj.learnwithfun.download.DownloadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, h11, h12, h13, h14, alpha, delate, button;
    Button[] buttons;
    Button bb1, bb2, bb3, bb4, bb5, bb6, bb7, bb8, bb9, bb10;
    Button[] bbuttons;
    Random random = new Random();
    String ans;
    int[] occupy = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] gotHint;
    int number;
    HashMap<Integer, Integer> filled;
    HashMap<Integer, Integer> ansHash;
    boolean b = false;
    LinearLayout now;
    RelativeLayout next;
    ImageView img, img2, img3, img4, gem1, gem2, gem3;
    ImageView[] imgs;
    int level = 0;
    TextView textLevel, textCoins, gemText;

    Dialog correctDialog, deleteDialog;
    Button yesc, noc, yesd, nod;
    TextView text,increaseLevel;
    ProgressBar progress;
    ImageView back;

    Animation bouncy;
    SharedPreferences sharedPreferences;

    int[][] imageList;
    String[] ansList;

    int count = 30;
    int coins=300;

    String[][] imageArray;
    AsyncTask myTask;
    HashMap<Integer, String> pics = new HashMap<>();

    ContextWrapper cw = new ContextWrapper(this);
    File file = null;

    boolean downloading=false;


    MediaPlayer ans_correct;
    MediaPlayer coins_i;
    MediaPlayer level_i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);

        count=sharedPreferences.getInt("count",30);
        coins=sharedPreferences.getInt("coins",300);
        level=sharedPreferences.getInt("level",0);

        ans_correct=MediaPlayer.create(this,R.raw.anscorrect);
        level_i=MediaPlayer.create(this,R.raw.levelin);
        coins_i=MediaPlayer.create(this,R.raw.coinsin);


        file = cw.getDir("imgDir", MODE_PRIVATE);

        filled = new HashMap<>();
        ansHash = new HashMap<>();

        textLevel = findViewById(R.id.levelText);
        now = findViewById(R.id.now);
        next = findViewById(R.id.next);

        gem1 = findViewById(R.id.gem1);
        gem2 = findViewById(R.id.gem2);
        gem3 = findViewById(R.id.gem3);
        textCoins = findViewById(R.id.coins);
        gemText = findViewById(R.id.gemtext);
        textCoins.setText(String.valueOf(coins));
        back = findViewById(R.id.back);
        increaseLevel=findViewById(R.id.increaseLevel);
        increaseLevel.setText(String.valueOf(level+1));

        progress = findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);

        imageList = Main2Activity.imageList;

        ansList = Main2Activity.ansList;

        imageArray = Main2Activity.imageArray;



        bouncy = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });


        correctDialog = new Dialog(MainActivity.this);
        correctDialog.setContentView(R.layout.correctword);
        yesc = correctDialog.findViewById(R.id.yes);
        noc = correctDialog.findViewById(R.id.no);
        text = correctDialog.findViewById(R.id.text);
        text.setText("do you want to reveal a correct letter for 50 coins");
        yesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coins >= 50) {
                    getHint();
                    coins = coins - 50;
                    textCoins.setText(String.valueOf(coins));

                }
                correctDialog.dismiss();
            }
        });
        noc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctDialog.dismiss();
            }
        });
        deleteDialog = new Dialog(MainActivity.this);
        deleteDialog.setContentView(R.layout.correctword);
        yesd = deleteDialog.findViewById(R.id.yes);
        nod = deleteDialog.findViewById(R.id.no);
        text = deleteDialog.findViewById(R.id.text);
        text.setText("Do you want to delete letters that are not part of the solution for 80 coins");
        yesd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coins >= 80 && !b) {
                    for (int i = 12; i < 26; i++) {
                        buttons[i].setVisibility(View.GONE);
                    }

                    for (int i = 0; i < ans.length(); i++) {
                        if (gotHint[i] == 0 && !String.valueOf(ans.charAt(i)).equals("-")) {
                            filled.remove(i);
                            buttons[i].setText("");
                            buttons[ansHash.get(i)].setVisibility(View.VISIBLE);
                        }
                    }
                    coins = coins - 80;
                    textCoins.setText(String.valueOf(coins));
                    b = true;
                }
                deleteDialog.dismiss();
            }
        });

        nod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDialog.dismiss();
            }
        });


        buttons = new Button[]{b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, h11, h12, h13, h14, alpha, delate};
        bbuttons = new Button[]{bb1, bb2, bb3, bb4, bb5, bb6, bb7, bb8, bb9, bb10};
        imgs = new ImageView[]{img, img2, img3, img4};


        button = findViewById(R.id.button);

        bbuttons[0] = (Button) findViewById(R.id.bb1);
        bbuttons[1] = (Button) findViewById(R.id.bb2);
        bbuttons[2] = (Button) findViewById(R.id.bb3);
        bbuttons[3] = (Button) findViewById(R.id.bb4);
        bbuttons[4] = (Button) findViewById(R.id.bb5);
        bbuttons[5] = (Button) findViewById(R.id.bb6);
        bbuttons[6] = (Button) findViewById(R.id.bb7);
        bbuttons[7] = (Button) findViewById(R.id.bb8);
        bbuttons[8] = (Button) findViewById(R.id.bb9);
        bbuttons[9] = (Button) findViewById(R.id.bb10);


        buttons[0] = (Button) findViewById(R.id.b1);

        buttons[1] = (Button) findViewById(R.id.b2);

        buttons[2] = (Button) findViewById(R.id.b3);

        buttons[3] = (Button) findViewById(R.id.b4);

        buttons[4] = (Button) findViewById(R.id.b5);

        buttons[5] = (Button) findViewById(R.id.b6);

        buttons[6] = (Button) findViewById(R.id.b7);

        buttons[7] = (Button) findViewById(R.id.b8);

        buttons[8] = (Button) findViewById(R.id.b9);

        buttons[9] = (Button) findViewById(R.id.b10);

        buttons[10] = (Button) findViewById(R.id.b11);

        buttons[11] = (Button) findViewById(R.id.b12);

        buttons[12] = (Button) findViewById(R.id.h1);

        buttons[13] = (Button) findViewById(R.id.h2);

        buttons[14] = (Button) findViewById(R.id.h3);

        buttons[15] = (Button) findViewById(R.id.h4);

        buttons[16] = (Button) findViewById(R.id.h5);

        buttons[17] = (Button) findViewById(R.id.h6);

        buttons[18] = (Button) findViewById(R.id.h7);

        buttons[19] = (Button) findViewById(R.id.h8);

        buttons[20] = (Button) findViewById(R.id.h9);

        buttons[21] = (Button) findViewById(R.id.h10);

        buttons[22] = (Button) findViewById(R.id.h11);

        buttons[23] = (Button) findViewById(R.id.h12);

        buttons[24] = (Button) findViewById(R.id.h13);

        buttons[25] = (Button) findViewById(R.id.h14);

        buttons[26] = (Button) findViewById(R.id.alpha);

        buttons[27] = (Button) findViewById(R.id.delete);

        imgs[0] = findViewById(R.id.img);

        imgs[1] = findViewById(R.id.img2);

        imgs[2] = findViewById(R.id.img3);

        imgs[3] = findViewById(R.id.img4);

        for (int i = 0;i < buttons.length; i++)
            buttons[i].setEnabled(false);
        set();


        button.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                button.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        coins_i.start();
                        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.won);
                        gem1.startAnimation(animation1);
                        gemText.setVisibility(View.GONE);
                    }
                }, 0);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        coins_i.start();
                        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.won);
                        gem2.startAnimation(animation2);
                    }
                }, 100);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        coins_i.start();
                        Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.won);
                        gem3.startAnimation(animation3);
                        animation3.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                gem1.setVisibility(View.INVISIBLE);
                                gem2.setVisibility(View.INVISIBLE);
                                gem3.setVisibility(View.INVISIBLE);
                                textCoins.setText(String.valueOf(coins));
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                    }
                }, 200);

                set();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        next.setVisibility(View.GONE);
                        now.setVisibility(View.VISIBLE);
                    }
                }, 600);
            }
        });

        for (int i = 0; i < 28; i++) {
            buttons[i].setOnClickListener(this);
        }
        runnable.run();
        new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runnable1.run();
                    }
                }, 500);
    }

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            buttons[26].startAnimation(bouncy);
            new Handler().postDelayed(runnable, 7000);
        }
    };

    final Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            buttons[27].startAnimation(bouncy);
            new Handler().postDelayed(runnable1, 7000);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b1:
                remove(0);
                break;
            case R.id.b2:
                remove(1);
                break;
            case R.id.b3:
                remove(2);
                break;
            case R.id.b4:
                remove(3);
                break;
            case R.id.b5:
                remove(4);
                break;
            case R.id.b6:
                remove(5);
                break;
            case R.id.b7:
                remove(6);
                break;
            case R.id.b8:
                remove(7);
                break;
            case R.id.b9:
                remove(8);
                break;
            case R.id.b10:
                remove(9);
                break;
            case R.id.b11:
                remove(10);
                break;
            case R.id.b12:
                remove(11);
                break;

            case R.id.h1:
                fill(12);
                break;
            case R.id.h2:
                fill(13);
                break;
            case R.id.h3:
                fill(14);
                break;
            case R.id.h4:
                fill(15);
                break;
            case R.id.h5:
                fill(16);
                break;
            case R.id.h6:
                fill(17);
                break;
            case R.id.h7:
                fill(18);
                break;
            case R.id.h8:
                fill(19);
                break;
            case R.id.h9:
                fill(20);
                break;
            case R.id.h10:
                fill(21);
                break;
            case R.id.h11:
                fill(22);
                break;
            case R.id.h12:
                fill(23);
                break;
            case R.id.h13:
                fill(24);
                break;
            case R.id.h14:
                fill(25);
                break;
            case R.id.alpha:
                correctDialog.show();
                break;
            case R.id.delete:
                deleteDialog.show();
                break;
        }
    }

    private void getHint() {
        int i = ans.indexOf('-');
        int h = check(i, random.nextInt(ans.length()));
        if (gotHint[h] == 0) {
            if (!buttons[h].getText().toString().equals("")) {
                buttons[filled.get(h)].setVisibility(View.VISIBLE);
            }
            buttons[h].setText(buttons[ansHash.get(h)].getText().toString());
            buttons[h].setTextColor(Color.CYAN);
            buttons[ansHash.get(h)].setVisibility(View.GONE);
            filled.put(h, ansHash.get(h));
            gotHint[h] = 1;
        } else {
            conflict();
        }
        isComplete();
    }

    private int check(int i, int hi) {
        int h = 0;
        if (i == hi) {
            check(i, random.nextInt(ans.length()));
        } else {
            h = hi;
        }
        return h;
    }

    private void conflict() {
        for (int i = 0; i < ans.length(); i++) {
            if (gotHint[i] == 0 && !String.valueOf(ans.charAt(i)).equals("-")) {
                buttons[i].setText(buttons[ansHash.get(i)].getText().toString());
                buttons[i].setTextColor(Color.CYAN);
                buttons[ansHash.get(i)].setVisibility(View.GONE);
                filled.put(i, ansHash.get(i));
                gotHint[i] = 1;
                break;
            }
        }
    }

    private void remove(int button) {
        if (!buttons[button].getText().equals("") && gotHint[button] == 0 && !String.valueOf(ans.charAt(button)).equals("-")) {
            int num = filled.get(button);
            filled.remove(button);
            buttons[button].setText("");
            buttons[num].setVisibility(View.VISIBLE);
        }
    }

    private void fill(int button) {
        for (int i = 0; i < ans.length(); i++) {
            if (buttons[i].getText().equals("")) {
                buttons[i].setText(String.valueOf(buttons[button].getText()));
                buttons[button].setVisibility(View.GONE);
                filled.put(i, button);
                isComplete();
                break;
            }
        }
    }

    private void isComplete() {

        for(int j=0;j<bbuttons.length;j++){
            bbuttons[j].setVisibility(View.VISIBLE);
        }
        char[] completed = new char[ans.length()];
        for (int i = 0; i < ans.length(); i++) {
            if (!buttons[i].getText().toString().equals("")) {
                completed[i] = buttons[i].getText().toString().charAt(0);
            }
        }

        String myAns = new String(completed);
        if (myAns.equals(ans)) {
            imgs[0].setBackgroundResource(0);
            imgs[1].setBackgroundResource(0);
            imgs[2].setBackgroundResource(0);
            imgs[3].setBackgroundResource(0);

            for (int i = 0; i < buttons.length; i++)
                buttons[i].setEnabled(false);
            button.setEnabled(true);

            for(int j=0;j<ans.length();j++){
                bbuttons[j].setText(buttons[j].getText());
            }
            for(int j=ans.length();j<bbuttons.length;j++){
                bbuttons[j].setVisibility(View.GONE);
            }

            ans_correct.start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    next.setVisibility(View.VISIBLE);
                    now.setVisibility(View.GONE);
                    gem1.setVisibility(View.VISIBLE);
                    gem2.setVisibility(View.VISIBLE);
                    gem3.setVisibility(View.VISIBLE);
                    gemText.setVisibility(View.VISIBLE);
                    for (int i = 0; i < 12; i++) {
                        occupy[i] = 0;
                    }
                    for (int i = 0; i < 12; i++) {
                        buttons[i].setText("");
                        buttons[i].setTextColor(Color.WHITE);
                    }

                    for (int i = 0; i < 12; i++) {
                        buttons[i].setText("");
                    }

                    imgs[0].setImageBitmap(null);
                    imgs[1].setImageBitmap(null);
                    imgs[2].setImageBitmap(null);
                    imgs[3].setImageBitmap(null);

                    for (int i = 0; i < 26; i++)
                        buttons[i].setVisibility(View.VISIBLE);
                    b = false;
                    level = level + 1;
                    coins = coins + 5;
                    sharedPreferences.edit().putInt("level",level).apply();
                    sharedPreferences.edit().putInt("coins",coins).apply();

                }
            }, 500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    level_i.start();
                    increaseLevel.setText(String.valueOf(level+1));
                }
            },1300);


            for (int i = 0; i < 12; i++) {

                buttons[i].setTextColor(Color.GREEN);
            }

        }
    }

    public void occupied() {
        int n = 12 + random.nextInt(12);
        if (occupy[n - 12] == 1) {
            occupied();
        } else
            number = n;
    }

    public void set() {

       if (level >= count - 15 && !downloading && count<count+imageArray.length) {
            downloading=true;
            final int y = count + 15;
            for (int i = count-30; i < imageArray.length; i++) {
                final int finalI = i;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myTask = new DownloadTask(getApplicationContext(),count).execute(imageArray[finalI][0], imageArray[finalI][1], imageArray[finalI][2], imageArray[finalI][3]);
                        count++;
                        if(finalI ==y-1)
                            downloading=false;
                    }
                },2000);

            }
        }
        for (int j = 0; j < 4; j++) {
            File del = new File(file, "img" + (level - 1) + "" + j + ".png");
            if (del.exists())
                del.delete();
        }
        textLevel.setText(String.valueOf(level + 1));
        progress.setVisibility(View.GONE);
        for (int i = 0; i < buttons.length; i++)
            buttons[i].setEnabled(true);
        textCoins.setText(String.valueOf(coins));
        ImageView temp = findViewById(R.id.img);
        for (int i = 0; i < 4; i++) {
            final int finalI = i;
            imgs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popup(finalI);
                }
            });
        }
        if (level < 30) {
            Picasso.with(getApplicationContext()).load(imageList[level][0]).fit().into(imgs[0]);
            Picasso.with(getApplicationContext()).load(imageList[level][1]).fit().into(imgs[1]);
            Picasso.with(getApplicationContext()).load(imageList[level][2]).fit().into(imgs[2]);
            Picasso.with(getApplicationContext()).load(imageList[level][3]).fit().into(imgs[3]);
        } else {

            for (int j = 0; j < 4; j++) {
                File name = new File(file, "img" + level + "" + j + ".png");
                String pic = new File(file, "img" + level + "" + j + ".png").getAbsolutePath();
                pics.put(Integer.parseInt(count + "" + j), pic);
                Picasso.with(getApplicationContext()).load(name).fit().into(imgs[j]);
            }
        }
        ans = ansList[level];
        gotHint = new int[ans.length()];
        for (int i = 0; i < ans.length(); i++) {
            gotHint[i] = 0;
        }

        for (int i = ans.length(); i < 12; i++) {
            buttons[i].setVisibility(View.GONE);
        }
        for (int i = 0; i < ans.length(); i++) {
            occupied();
            if (!String.valueOf(ans.charAt(i)).equals("-")) {
                buttons[number].setText(String.valueOf(ans.charAt(i)));
                ansHash.put(i, number);
                occupy[number - 12] = 1;
            } else
                buttons[i].setText("-");

        }
        for (int i = 12; i < 26; i++) {
            if (occupy[i - 12] == 0) {
                buttons[i].setText(String.valueOf((char) (65 + random.nextInt(26))));
                occupy[i - 12] = 1;
            }

        }

    }

    private void popup(int position) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.pop);
        ImageView view = dialog.findViewById(R.id.imgpop);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        if (level > 29) {
            Picasso.with(getApplicationContext()).load(new File(pics.get(Integer.parseInt(level+1 + "" + position)))).into(view);
        } else
            Picasso.with(getApplicationContext()).load(imageList[level][position]).into(view);
        dialog.setCancelable(true);
        dialog.show();

    }

    public void watchVideo(View view) {
    }
}

