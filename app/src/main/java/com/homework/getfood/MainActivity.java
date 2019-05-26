package com.homework.getfood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
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

import com.homework.getfood.bean.FoodBean;
import com.homework.getfood.context.AppContext;
import com.homework.getfood.util.IconFetcher;
import com.homework.getfood.util.NoScrollViewPager;
import com.homework.getfood.util.StringFetcher;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static MakeFragment fragment_make;
    private static OrderFragment fragment_order;
    private Fragment[] fragments;
    private TabLayout mTabLayout;
    static private NoScrollViewPager mViewPager;
    private MainFragmentPagerAdapter FragmentPagerAdapter;
    private AppContext globalFood = (AppContext) getApplication();
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
        mViewPager = (NoScrollViewPager) findViewById(R.id.viewPager);
        mViewPager.setScroll(false);
        FragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager()){
            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        };
        mViewPager.setAdapter(FragmentPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        makeOrder = mTabLayout.getTabAt(0).setIcon(ICON[0]);
        listOrder = mTabLayout.getTabAt(1).setIcon(ICON[1]);
        fragment_order = (OrderFragment) this.getSupportFragmentManager().findFragmentById(R.id.fragment_order_id);
        fragment_make = (MakeFragment) this.getSupportFragmentManager().findFragmentById(R.id.fragment_make_id);
    }

    public static void setViewPagerID(int id){
        mViewPager.setCurrentItem(id);
    }
    public static OrderFragment getFragment_order(){
        return fragment_order;
    }

    public static void setFragment_order(OrderFragment fragment_order) {
        MainActivity.fragment_order = fragment_order;
    }
    public static void setFragment_make(MakeFragment fragment_make){
        MainActivity.fragment_make = fragment_make;
    }
    public static MakeFragment getFragment_make(){
        return fragment_make;
    }
}
