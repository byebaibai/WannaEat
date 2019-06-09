package com.homework.getfood.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.homework.getfood.util.CartListener;
import com.homework.getfood.ui.fragment.MakeFragment;
import com.homework.getfood.R;
import com.homework.getfood.bean.FoodBean;
import com.homework.getfood.context.AppContext;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 购物车ListView的Adapter
 */
public class CartAdapter extends BaseAdapter {

    private ArrayList<FoodBean> foodList; //购物车内的食品数据
    private LayoutInflater mInflater;
    private CartListener cartListener; //监听对购物车内的商品操作动作
    public void setCartListener(CartListener callBackListener) {
        this.cartListener = callBackListener;
    }

    public CartAdapter(Context context, HashMap<String, FoodBean> foodProducts){
        mInflater = LayoutInflater.from(context);
        foodList  = new ArrayList<FoodBean>();
        foodList.addAll(foodProducts.values());
    }

    /**
     * 更新购物车显示
     */
    public void updateData(){
        foodList = new ArrayList<FoodBean>();
        foodList.addAll(AppContext.getCart().values());
        MakeFragment.setPrice();
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

    @SuppressLint("SetTextI18n")
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
            public void onClick(View v) { // 增加购物车内某商品
                FoodBean food = foodList.get(position);
                int num = food.getCartNum();
                num++;
                foodList.get(position).setCartNum(num);
                viewHolder.shoppingNum.setText(foodList.get(position).getCartNum()+"");
                AppContext.getCart().remove(food.getName()); // 更新全局该商品购物车数据
                AppContext.getCart().put(food.getName(),food);
                updateData();
            }
        });

        viewHolder.reduce.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) { // 减少购物车内某商品
                FoodBean food = foodList.get(position);
                int num = food.getCartNum();
                if (num > 0) {
                    num--;
                    if(num==0){
                        cartListener.onRemoveFood(foodList.get(position)); // 该商品数目为0，则从购物车内移除
                    }else {
                        food.setCartNum(num);
                        viewHolder.shoppingNum.setText(foodList.get(position).getCartNum()+"");
                        AppContext.getCart().remove(food.getName()); // 更新全局该商品购物车数据
                        AppContext.getCart().put(food.getName(),food);
                    }
                    updateData();
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        public TextView commodityName; // 购物车商品名称
        public TextView commodityPrise; // 购物车商品价格
        public ImageButton increase; // 增加
        public ImageButton reduce; // 减少
        public TextView shoppingNum; // 商品数目
    }
}
