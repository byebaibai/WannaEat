package com.homework.getfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.homework.getfood.bean.FoodBean;
import com.homework.getfood.context.AppContext;
import com.homework.getfood.util.IconFetcher;

import java.util.ArrayList;

public class OrderFoodAdapter extends BaseAdapter {
    private ArrayList<FoodBean> foodList;
    private LayoutInflater mInflater;
    private Context mContext;

    public OrderFoodAdapter(Context context,ArrayList<FoodBean> list){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        foodList = list;
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
    public View getView(final int position, View convertView, ViewGroup parent){
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_food_order,null);
            viewHolder = new ViewHolder();
            viewHolder.foodImage = (ImageView) convertView.findViewById(R.id.image_food_item);
            viewHolder.foodName = (TextView) convertView.findViewById(R.id.text_food_item);
            viewHolder.foodNumber = (TextView) convertView.findViewById(R.id.text_food_num);
            viewHolder.foodPrice = (TextView) convertView.findViewById(R.id.text_food_price);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        int iconID = IconFetcher.getResId(foodList.get(position).getIcon(),R.drawable.class);
        viewHolder.foodImage.setImageResource(iconID);
        viewHolder.foodName.setText(foodList.get(position).getName());
        viewHolder.foodNumber.setText(foodList.get(position).getCartNum().toString());
        viewHolder.foodPrice.setText("¥ " + foodList.get(position).getPrice().toString());

        return convertView;
    }
    class ViewHolder {
        public ImageView foodImage;
        public TextView foodName;
        public TextView foodNumber;
        public TextView foodPrice;
    }
}
