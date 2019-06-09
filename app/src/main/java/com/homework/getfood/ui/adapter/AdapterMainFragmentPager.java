package com.homework.getfood.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.homework.getfood.ui.fragment.FragmentMake;
import com.homework.getfood.ui.fragment.FragmentOrder;
import com.homework.getfood.R;
import com.homework.getfood.util.StringFetcher;

public class AdapterMainFragmentPager extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{StringFetcher.getString(R.string.title_home), StringFetcher.getString(R.string.title_orders)};

    public AdapterMainFragmentPager(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 1) return new FragmentOrder();
        else return new FragmentMake();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }


}
