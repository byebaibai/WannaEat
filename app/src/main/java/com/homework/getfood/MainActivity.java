package com.homework.getfood;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.homework.getfood.context.AppContext;
import com.homework.getfood.util.NoScrollViewPager;

public class MainActivity extends AppCompatActivity {
    private static MakeFragment fragment_make;
    private static OrderFragment fragment_order;
    private Fragment[] fragments;
    private TabLayout mTabLayout;
    static private NoScrollViewPager mViewPager;
    private MainFragmentPagerAdapter FragmentPagerAdapter;
    private AppContext globalFood = (AppContext) getApplication();
    private TabLayout.Tab makeOrder;
    private long time = 0;
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /* 点击的为返回键 */
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();// 退出方法
        }
        return true;
    }

    private void exit() {
        if (System.currentTimeMillis() - time > 2000) {
            time = System.currentTimeMillis();
            showToast("再点击一次退出应用程序");
        }else{
            this.finishAffinity();
            System.exit(0);
        }
    }
    @SuppressLint("WrongConstant")
    public void showToast(String text) {
        Toast.makeText(this, text, 2000).show();
    }

}
