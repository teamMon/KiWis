package com.example.jpar4.kiwis.kiwis;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.jpar4.kiwis.R;

public class StartingActivity extends AppCompatActivity {
    ImageView starting_img;
    AnimationDrawable drawable;
    BackgroundTask1 task;
    BackgroundTask2 task2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        starting_img = (ImageView) findViewById(R.id.starting_img);
        drawable = (AnimationDrawable) starting_img.getBackground();
        task = new BackgroundTask1();
        task.execute();


/*        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);
        starting_img.setImageResource(R.drawable.frame11);
        starting_img.startAnimation(animation);*/

    }

    class BackgroundTask1 extends AsyncTask{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            drawable.start();
            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            drawable.stop();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            starting_img.setBackgroundColor(Color.WHITE);
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);
            starting_img.setImageResource(R.drawable.skytower);
            starting_img.startAnimation(animation);

            task2 = new BackgroundTask2();
            task2.execute();
        }

    }
    class BackgroundTask2 extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                Thread.sleep(1800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Intent intent = new Intent(StartingActivity.this,  LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.not_move_activity, R.anim.alpha);
            finish();
        }
    }
}

      /*  Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);
        starting_img.setImageResource(R.drawable.skytower0);
        starting_img.startAnimation(animation);
        starting_img.setImageResource(R.drawable.skytower1);
        starting_img.startAnimation(animation);
        starting_img.setImageResource(R.drawable.skytower2);
        starting_img.startAnimation(animation);
        starting_img.setImageResource(R.drawable.skytower3);
        starting_img.startAnimation(animation);
        starting_img.setImageResource(R.drawable.skytower4);
        starting_img.startAnimation(animation);
        starting_img.setImageResource(R.drawable.skytower5);
        starting_img.startAnimation(animation);
        starting_img.setImageResource(R.drawable.skytower6);
        starting_img.startAnimation(animation);
        starting_img.setImageResource(R.drawable.skytower7);
        starting_img.startAnimation(animation);
        starting_img.setImageResource(R.drawable.skytower8);
        starting_img.startAnimation(animation);
        starting_img.setImageResource(R.drawable.skytower9);
        starting_img.startAnimation(animation);
        starting_img.setImageResource(R.drawable.skytower10);
        starting_img.startAnimation(animation);
        starting_img.setImageResource(R.drawable.skytower11);
        starting_img.startAnimation(animation);
        starting_img.setImageResource(R.drawable.skytower12);
        starting_img.startAnimation(animation);
        starting_img.setImageResource(R.drawable.skytower13);
        starting_img.startAnimation(animation);*/