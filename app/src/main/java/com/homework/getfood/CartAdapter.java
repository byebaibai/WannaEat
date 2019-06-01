package com.homework.getfood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.homework.getfood.bean.FoodBean;
import com.homework.getfood.context.AppContext;

import java.util.ArrayList;
import java.util.HashMap;

public class CartAdapter extends BaseAdapter {

    private ArrayList<FoodBean> foodList;
    private LayoutInflater mInflater;
    private CartListener cartListener;
    public void setCartListener(CartListener callBackListener) {
        this.cartListener = callBackListener;
    }

    public CartAdapter(Context context, HashMap<String, FoodBean> foodProducts){
        mInflater = LayoutInflater.from(context);
        foodList  = new ArrayList<FoodBean>();
        foodList.addAll(foodProducts.values());
    }

    public void updateData(){
        foodList = new ArrayList<FoodBean>();
        foodList.addAll(AppContext.getCart().values());
        MakeFragment.setPrice();
        System.out.println(foodList.size());
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_cart_item, null);
            viewHolder = new ViewHolder();
            viewHolder.commodityName = (TextView) convertView.findViewById(R.id.text_item_name);
            viewHolder.commodityPrise = (TextView) convertView.findViewById(R.id.text_item_price);
            viewHolder.increase = (ImageButton)  convertView.findViewById(R.id.cartAddButton);
            viewHolder.reduce = (ImageButton)  convertView.findViewById(R.id.cartSubButton);
            viewHolder.shoppingNum = (TextView)  convertView.findViewById(R.id.text_item_num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.commodityName.setText(foodList.get(position).getName());
        viewHolder.commodityPrise.setText(foodList.get(position).getPrice().toString());
        viewHolder.shoppingNum.setText(foodList.get(position).getCartNum().toString());
        viewHolder.increase.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                FoodBean food = foodList.get(position);
                int num = food.getCartNum();
                num++;
                foodList.get(position).setCartNum(num);
                viewHolder.shoppingNum.setText(foodList.get(position).getCartNum()+"");
                AppContext.getCart().remove(food.getName());
                AppContext.getCart().put(food.getName(),food);
                updateData();
            }
        });

        viewHolder.reduce.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                FoodBean food = foodList.get(position);
                int num = food.getCartNum();
                if (num > 0) {
                    num--;
                    if(num==0){
                        cartListener.onRemoveFood(foodList.get(position));
                    }else {
                        food.setCartNum(num);
                        viewHolder.shoppingNum.setText(foodList.get(position).getCartNum()+"");
                        AppContext.getCart().remove(food.getName());
                        AppContext.getCart().put(food.getName(),food);
                    }
                    updateData();
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        /**
         * 购物车商品名称
         */
        public TextView commodityName;
        /**
         * 购物车商品价格
         */
        public TextView commodityPrise;

        /**
         * 增加
         */
        public ImageButton increase;
        /**
         * 减少
         */
        public ImageButton reduce;
        /**
         * 商品数目
         */
        public TextView shoppingNum;
    }
}
