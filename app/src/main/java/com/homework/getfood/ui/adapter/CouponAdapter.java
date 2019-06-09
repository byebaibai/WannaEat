package com.homework.getfood.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.homework.getfood.R;
import com.homework.getfood.bean.CouponBean;

import java.util.ArrayList;

/**
 * 优惠券ListView的Adapter
 */
public class CouponAdapter extends BaseAdapter {
    public ArrayList<CouponBean> couponBeanArrayList; // 可使用的优惠券列表
    private Integer minusTotal; // 使用优惠券后减去的价格
    private LayoutInflater mInflater;
    private Context mContext;
    private Integer totalCost;
    public CouponAdapter(Context context, ArrayList<CouponBean> coupon, Integer totalCost, Boolean isGroup){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        couponBeanArrayList = coupon;
        this.totalCost = totalCost;
        minusTotal = 0;
        if (!isGroup) { // 如果购物车内不含套餐则使用优惠券
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
            convertView = mInflater.inflate(R.layout.adapter_coupon_item,null);
            viewHolder = new CouponAdapter.ViewHolder();
            viewHolder.couponName = (TextView) convertView.findViewById(R.id.couponName);
            viewHolder.couponCost = (TextView) convertView.findViewById(R.id.minusCost);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (CouponAdapter.ViewHolder) convertView.getTag();
        }
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
