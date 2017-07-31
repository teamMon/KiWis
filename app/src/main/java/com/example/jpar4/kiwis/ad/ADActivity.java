package com.example.jpar4.kiwis.ad;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.jpar4.kiwis.R;

public class ADActivity extends AppCompatActivity {
    Handler handler;
    ADRunnable runnable;
    NewsRunnable news_runnable;
    boolean isRunning;

    ImageView iv_ad;
    ImageView iv_news;
    int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        handler = new Handler();
        runnable = new ADRunnable();
        news_runnable = new NewsRunnable();


        iv_ad = (ImageView) findViewById(R.id.iv_ad);
        iv_news = (ImageView) findViewById(R.id.iv_news);
        int page = 1;
    }


    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                  while(isRunning){
                        Thread.sleep(5000);
                        // Message msg = handler.obtainMessage();
                        // handler.sendMessage(msg);
                        handler.post(runnable);
                    }
                } catch (Exception e) {

                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(isRunning){
                        Thread.sleep(3500);

                        // Message msg = handler.obtainMessage();
                        // handler.sendMessage(msg);
                        handler.post(news_runnable);
                    }
                } catch (Exception e) {

                }
            }
        });
        thread2.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
    }

    public class ADRunnable implements Runnable {
        @Override
        public void run() {
            ++page;
            if(page>5){
                page=1;
            }
           switch(page){
               case 1:
                   iv_ad.setImageResource(R.drawable.ad1);
                   break;
               case 2:
                   iv_ad.setImageResource(R.drawable.ad2);
                   break;
               case 3:
                   iv_ad.setImageResource(R.drawable.ad3);
                   break;
               case 4:
                   iv_ad.setImageResource(R.drawable.ad4);
                   break;
               case 5:
                   iv_ad.setImageResource(R.drawable.ad5);
                   break;
               default:

                   break;
           }

        }
    }

    public class NewsRunnable implements Runnable {
        @Override
        public void run() {
            ++page;
            if(page>7){
                page=1;
            }
            switch(page){
                case 1:
                    iv_news.setImageResource(R.drawable.news1);
                    break;
                case 2:
                    iv_news.setImageResource(R.drawable.news2);
                    break;
                case 3:
                    iv_news.setImageResource(R.drawable.news3);
                    break;
                case 4:
                    iv_news.setImageResource(R.drawable.news4);
                    break;
                case 5:
                    iv_news.setImageResource(R.drawable.news5);
                    break;
                case 6:
                    iv_news.setImageResource(R.drawable.news6);
                    break;
                case 7:
                    iv_news.setImageResource(R.drawable.news7);
                    break;
                default:

                    break;
            }

        }
    }
}
