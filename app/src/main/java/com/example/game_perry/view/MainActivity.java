package com.example.game_perry.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game_perry.R;

import androidx.appcompat.widget.AppCompatImageView;


import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;


import java.util.Random;
public class MainActivity extends AppCompatActivity {

    private final int ROWS =6;
    private final int COLUNMS =3;
    private int DELAY1 = 500;
    Activity a = new Activity();
    private AppCompatImageView game_IMG_backyard;
    private AppCompatImageView[][] game_IMG_characters;
    private AppCompatImageView[] game_IMG_hearts;
    private AppCompatImageView[] game_IMG_perrys;
    private int lives = 3;
    private MaterialButton game_BTN_right;
    private MaterialButton game_BTN_left;
    private MaterialButton game_BTN_pause;
    private MaterialButton game_BTN_restart;
    private boolean pause = false;
    private Random rn = new Random();
    private boolean send=true;
    private enum direction {right, left}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        updateUI();
        initViews();
    }

    private void updateUI() {
        updateLives();
        moveDownUI();
        updateCharacters();
    }

    @Override
    protected void onResume() {
        this.pause = false;
        super.onResume();
        startTimer();
    }

    @Override
    protected void onPause() {
        if(pause==false) {
            this.pause = true;
            super.onPause();
            stopTimer();
        }
        else onResume();
    }
    @Override
    protected void onRestart() {
        pause = false;
        super.onRestart();
        startTimer();
        for (int i = 0; i < COLUNMS; i++) {
            for (int j = 0; j < ROWS; j++) {
                game_IMG_characters[j][i].setVisibility(View.INVISIBLE);
            }
        }
        game_IMG_perrys[0].setVisibility(View.INVISIBLE);
        game_IMG_perrys[2].setVisibility(View.INVISIBLE);
        game_IMG_perrys[1].setVisibility(View.VISIBLE);
        DELAY1 = 500;
        lives = 3;
        updateUI();
        initViews();
    }


    private void findViews() {

        game_IMG_hearts = new AppCompatImageView[]
                {findViewById(R.id.game_IMG_heart1),
                        findViewById(R.id.game_IMG_heart2),
                        findViewById(R.id.game_IMG_heart3)};

        game_IMG_characters = new AppCompatImageView[][]
                {{findViewById(R.id.game_IMG_phineas_1),
                        findViewById(R.id.game_IMG_ferb_2),
                        findViewById(R.id.game_IMG_candace3)},
                        {findViewById(R.id.game_IMG_phineas4),
                                findViewById(R.id.game_IMG_ferb5),
                                findViewById(R.id.game_IMG_candace6)},
                        {findViewById(R.id.game_IMG_phineas7),
                                findViewById(R.id.game_IMG_ferb8),
                                findViewById(R.id.game_IMG_candace9)},
                        {findViewById(R.id.game_IMG_phineas10),
                                findViewById(R.id.game_IMG_ferb11),
                                findViewById(R.id.game_IMG_candace12)},
                        {findViewById(R.id.game_IMG_phineas13),
                                findViewById(R.id.game_IMG_ferb14),
                                findViewById(R.id.game_IMG_candace15)},
                        {findViewById(R.id.game_IMG_phineas16),
                                findViewById(R.id.game_IMG_ferb17),
                                findViewById(R.id.game_IMG_candace18)}};

        game_IMG_perrys = new AppCompatImageView[]
                {findViewById(R.id.game_IMG_perry1),
                        findViewById(R.id.game_IMG_perry2),
                        findViewById(R.id.game_IMG_perry3)};

        game_BTN_left = findViewById(R.id.game_BTN_left);
        game_BTN_right = findViewById(R.id.game_BTN_right);
        game_BTN_pause = findViewById(R.id.game_BTN_pause);
        game_BTN_restart = findViewById(R.id.game_BTN_restart);
        game_IMG_backyard = findViewById(R.id.game_IMG_backyard);

        for (int i = 0; i < COLUNMS; i++) {
            for (int j = 0; j < ROWS; j++) {
                game_IMG_characters[j][i].setVisibility(View.INVISIBLE);
            }
        }
        game_IMG_perrys[0].setVisibility(View.INVISIBLE);
        game_IMG_perrys[2].setVisibility(View.INVISIBLE);
        game_IMG_perrys[1].setVisibility(View.VISIBLE);

    }


    private void initViews() {

        game_BTN_left.setOnClickListener(v -> moveLeft());
        game_BTN_right.setOnClickListener(v -> moveRight());
        game_BTN_pause.setOnClickListener(v -> onPause());
        game_BTN_restart.setOnClickListener(v -> onRestart());
    }

    private void reduceLives() {lives--;}

    private void updateLives() {
        for (int i = 0; i < lives; i++) {
            game_IMG_hearts[i].setVisibility(View.VISIBLE);
        }

        for (int i = lives; i < game_IMG_hearts.length; i++) {
            game_IMG_hearts[i].setVisibility(View.INVISIBLE);
        }
        if (lives == 0) gameOver();
    }

    private void gameOver() {
        pause=true;
        for (int i = 0; i < COLUNMS; i++) {
            for (int j = 0; j < ROWS; j++) {
                game_IMG_characters[j][i].setVisibility(View.INVISIBLE);
            }
        }
        stopTimer();
    }

    private Handler handler = new Handler();
    private Runnable runnable1 = new Runnable() {
        public void run() {
            handler.postDelayed(runnable1, DELAY1);
            updateUI();
        }
    };
    private void startTimer() {
        handler.postDelayed(runnable1, DELAY1);
    }

    private void stopTimer() {
        handler.removeCallbacks(runnable1);
    }

    private void updateLocationUI(int location, direction direction) {
        if(pause==false) {
            if (direction == direction.right) {
                game_IMG_perrys[location - 1].setVisibility(View.INVISIBLE);
                game_IMG_perrys[location].setVisibility(View.VISIBLE);
            } else {
                game_IMG_perrys[location + 1].setVisibility(View.INVISIBLE);
                game_IMG_perrys[location].setVisibility(View.VISIBLE);
            }
            checkHit();
        }
    }

    private void moveRight() {
        if (game_IMG_perrys[2].getVisibility() != View.VISIBLE) {
            if (game_IMG_perrys[0].getVisibility()==View.VISIBLE) {
                updateLocationUI(1, direction.right);
            } else {
                updateLocationUI(2, direction.right);
            }
        }
    }

    private void moveLeft() {
        if (game_IMG_perrys[0].getVisibility() != View.VISIBLE) {
            if (game_IMG_perrys[1].getVisibility()==View.VISIBLE) {
                updateLocationUI(0, direction.left);
            } else {
                updateLocationUI(1, direction.left);
            }
        }
    }


    private void checkHit() {
        for (int i = 0; i < COLUNMS; i++) {
            if (game_IMG_characters[ROWS-1][i].getVisibility()==View.VISIBLE && game_IMG_perrys[i].getVisibility()==View.VISIBLE) {
                reduceLives();
                vibrate();
                if(lives==2) Toast.makeText(this, "you need to be careful", Toast.LENGTH_SHORT).show();
                if(lives==1) Toast.makeText(this, "perry!!!", Toast.LENGTH_SHORT).show();
                if(lives==0) Toast.makeText(this, "BUSTED", Toast.LENGTH_SHORT).show();
                updateLives();
            }
        }
    }


    private void updateCharacters() {
        if (send) {
            int i = rn.nextInt(3);
            game_IMG_characters[0][i].setVisibility(View.VISIBLE);
        }
        send = !send;
        DELAY1--;
    }

    private void moveDownUI() {
        if (pause == false) {
            for (int j = 0; j < COLUNMS; j++) {
                game_IMG_characters[ROWS-1][j].setVisibility(View.INVISIBLE);
            }
            for (int j = 0; j < COLUNMS; j++) {
                for (int i = ROWS-1; i > 0; i--) {
                    if (game_IMG_characters[i - 1][j].getVisibility() == View.VISIBLE) {
                        game_IMG_characters[i - 1][j].setVisibility(View.INVISIBLE);
                        game_IMG_characters[i][j].setVisibility(View.VISIBLE);
                    }
                }
            }
            checkHit();
        }
    }

    protected void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(100);
        }
    }

}
