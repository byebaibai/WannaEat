package com.homework.getfood;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.homework.getfood.util.IconFetcher;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private MakeFragment fragment_make;
    private OrderFragment fragment_order;
    private Fragment[] fragments;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MainFragmentPagerAdapter FragmentPagerAdapter;

    private TabLayout.Tab makeOrder;
    private TabLayout.Tab listOrder;

    final int[] ICON = new int[]{
            R.drawable.ic_home_black_24dp,
            R.drawable.ic_notifications_black_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews(){
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        FragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(FragmentPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        makeOrder = mTabLayout.getTabAt(0).setIcon(ICON[0]);
        listOrder = mTabLayout.getTabAt(1).setIcon(ICON[1]);

    }


}
