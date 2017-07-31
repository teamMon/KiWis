package com.example.jpar4.kiwis.kiwis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.jpar4.kiwis.R;
import com.example.jpar4.kiwis.kiwis.fragment.ADFragment;
import com.example.jpar4.kiwis.kiwis.fragment.MemoryGameFragment;
import com.example.jpar4.kiwis.kiwis.fragment.ProfileFragment;
import com.example.jpar4.kiwis.kiwis.fragment.RecycleFragment;
import com.example.jpar4.kiwis.kiwis.func.BackPressCloseHandler;


public class KiwisMainActivity extends AppCompatActivity {
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiwis_main);

        backPressCloseHandler = new BackPressCloseHandler(this);

        //viewr객체 가져옴
        TabLayout tabLayout = (TabLayout) findViewById(R.id.kiwis_tl_tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.kiwis_vp_pager);
       // viewPager.setOffscreenPageLimit(1);
        //프레그먼트 생성
        Fragment[] arrFragments = new Fragment[4];
        arrFragments[0] = new RecycleFragment();
        arrFragments[1] = new ADFragment();
        arrFragments[2] = new MemoryGameFragment();
        arrFragments[3] = new ProfileFragment();

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), arrFragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        private  Fragment[] arrFragments;

        public MyPagerAdapter(FragmentManager fm, Fragment[] arrFragments) {
            super(fm);
            this.arrFragments=arrFragments;
        }

        @Override
        public Fragment getItem(int position) {
            return arrFragments[position];
        }

        @Override
        public int getCount() {
            return arrFragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "HOME";
                case 1:
                return "AD";
                case 2:
                return "GAME";
                case 3:
                    return "PROFILE";
                default:
                    return "";
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1002 && resultCode == Activity.RESULT_OK){
            //Toast.makeText(this, "222"+data.getStringExtra("content")+requestCode+resultCode, Toast.LENGTH_SHORT).show();
            //this.getFragmentManager().findFragmentById(R.id.kiwis_rv_list).onActivityResult(requestCode,resultCode,data);
            //this.getSupportFragmentManager().findFragmentById(R.id.kiwis_rv_list).onActivityResult(requestCode,resultCode,data);
            //getFragmentManager().findFragmentById(R.id.kiwis_rv_list)
            this.getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.kiwis_vp_pager+":0").onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}
