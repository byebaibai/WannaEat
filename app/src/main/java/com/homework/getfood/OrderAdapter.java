package com.homework.getfood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.content.Intent;

import com.homework.getfood.bean.OrderBean;
import com.homework.getfood.context.AppContext;

import java.util.ArrayList;
public class OrderAdapter extends BaseAdapter{
    public ArrayList<OrderBean> orderList;
    private LayoutInflater mInflater;
    private Context mContext;
    public OrderAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        orderList = AppContext.getOrderBeanArrayList();
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        this.notifyDataSetChanged();
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_order,null);
            viewHolder = new ViewHolder();
            viewHolder.orderTime = (TextView) convertView.findViewById(R.id.order_time);
            viewHolder.orderDetail = (TextView) convertView.findViewById(R.id.text_detail);
            viewHolder.orderGoods = (TextView) convertView.findViewById(R.id.order_goods);
            viewHolder.orderID = (TextView) convertView.findViewById(R.id.orderID);
            viewHolder.orderPrice = (TextView) convertView.findViewById(R.id.order_price);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.orderPrice.setText(orderList.get(position).getOrderPrice().toString() + " ¥");
        viewHolder.orderID.setText(orderList.get(position).getOrderID().toString());
        viewHolder.orderTime.setText(orderList.get(position).getOrderTime());
        String s = orderList.get(position).getOrderFoodList().get(0).getName() + "等商品";
        viewHolder.orderGoods.setText(s);
        viewHolder.orderDetail.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,OrderActivity.class);
                OrderActivity.setOrderData(orderList.get(position));
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        public TextView orderTime;

        public TextView orderGoods;

        public TextView orderID;

        public TextView orderPrice;

        public TextView orderDetail;
    }
}
