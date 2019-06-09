package com.homework.getfood.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.homework.getfood.R;
import com.homework.getfood.bean.OrderBean;
import com.homework.getfood.context.AppContext;
import com.homework.getfood.ui.activity.ActivityOrder;

import java.util.ArrayList;

/**
 * 订单页面订单ListView的Adapter
 */
public class AdapterOrder extends BaseAdapter{
    public ArrayList<OrderBean> orderList;
    private LayoutInflater mInflater;
    private Context mContext;
    public AdapterOrder(Context context){
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
            convertView = mInflater.inflate(R.layout.adapter_order_item,null);
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
        System.out.println(orderList.get(position).getOrderPrice());
        viewHolder.orderPrice.setText(orderList.get(position).getOrderActualPrice().toString() + " ¥");
        viewHolder.orderID.setText(orderList.get(position).getOrderID().toString());
        viewHolder.orderTime.setText(orderList.get(position).getOrderTime());
        Integer n = orderList.get(position).getFoodNumber();
        if (n == null) n = 1;
        String s;
        if (n > 1) s = orderList.get(position).getOrderFoodList().get(0).getName() + "等 " + n.toString() + " 份食物";
        else s = orderList.get(position).getOrderFoodList().get(0).getName();
        viewHolder.orderGoods.setText(s);
        viewHolder.orderDetail.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ActivityOrder.class);
                ActivityOrder.setOrderData(orderList.get(position));
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        public TextView orderTime; // 订单时间

        public TextView orderGoods; // 订单食物

        public TextView orderID; // 订单ID

        public TextView orderPrice; // 订单价格

        public TextView orderDetail; // 订单详情按钮
    }
}
