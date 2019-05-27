package com.homework.getfood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.homework.getfood.bean.CouponBean;
import com.homework.getfood.context.AppContext;

import java.util.ArrayList;

public class CouponAdapter extends BaseAdapter {
    public ArrayList<CouponBean> couponBeanArrayList;
    private Integer minusTotal;
    private LayoutInflater mInflater;
    private Context mContext;
    private Integer totalCost;
    public CouponAdapter(Context context, ArrayList<CouponBean> coupon,Integer totalCost,Boolean isGroup){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        couponBeanArrayList = coupon;
        this.totalCost = totalCost;
        minusTotal = 0;
        if (!isGroup) {
            for (CouponBean cb : coupon) {
                minusTotal += cb.getCost(totalCost);
            }
        }
    }

    @Override
    public int getCount() {
        return couponBeanArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return couponBeanArrayList.get(position);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        this.notifyDataSetChanged();
        final CouponAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_coupon,null);
            viewHolder = new CouponAdapter.ViewHolder();
            viewHolder.couponName = (TextView) convertView.findViewById(R.id.couponName);
            viewHolder.couponCost = (TextView) convertView.findViewById(R.id.minusCost);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (CouponAdapter.ViewHolder) convertView.getTag();
        }
        minusTotal += couponBeanArrayList.get(position).getCost(totalCost);
        System.out.println(minusTotal);
        viewHolder.couponName.setText(couponBeanArrayList.get(position).getName());
        viewHolder.couponCost.setText(couponBeanArrayList.get(position).getCost(totalCost).toString());
        return convertView;
    }

    public Integer getMinusTotal() {
        return minusTotal;
    }

    class ViewHolder {
        public TextView couponName;

        public TextView couponCost;
    }
}
