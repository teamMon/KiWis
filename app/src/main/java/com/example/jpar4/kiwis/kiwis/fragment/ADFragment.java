package com.example.jpar4.kiwis.kiwis.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jpar4.kiwis.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ADFragment extends Fragment {
    Handler handler;
    ADRunnable runnable;
    NewsRunnable news_runnable;
    Thread thread1;
    Thread thread2;
    boolean isRunning;

    ImageView iv_ad;
    ImageView iv_news;
    int page;


    public ADFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ad, container, false);
        handler = new Handler();
        runnable = new ADRunnable();
        news_runnable = new NewsRunnable();


        iv_ad = (ImageView) view.findViewById(R.id.iv_ad);
        iv_news = (ImageView) view.findViewById(R.id.iv_news);
        int page = 1;
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        isRunning = true;

       thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(isRunning){
                        Thread.sleep(3000);
                        handler.post(runnable);
                    }
                } catch (Exception e) {

                }
            }
        });
        thread1.start();

        thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(isRunning){
                        Thread.sleep(2000);
                        handler.post(news_runnable);
                    }
                } catch (Exception e) {

                }
            }
        });
        thread2.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        isRunning = false;
        thread1.interrupt();
        thread2.interrupt();

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
       //     isRunning = true;

/*            thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while(isRunning){
                            Thread.sleep(3000);
                            handler.post(runnable);
                        }
                    } catch (Exception e) {

                    }
                }
            });
            thread1.start();

            thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while(isRunning){
                            Thread.sleep(2000);
                            handler.post(news_runnable);
                        }
                    } catch (Exception e) {

                    }
                }
            });
            thread2.start();*/

        }else{
            //isRunning = false;
//            thread1.interrupt();
//            thread2.interrupt();
        }
    }
}
