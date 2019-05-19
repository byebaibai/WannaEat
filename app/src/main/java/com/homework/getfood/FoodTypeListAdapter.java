package com.homework.getfood;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.homework.getfood.R;

public class FoodTypeListAdapter extends BaseAdapter {
    private String[] leftStr;
    boolean[] flagArray;
    private Context context;

    public FoodTypeListAdapter(Context context, String[] leftStr, boolean[] flagArray) {
        this.leftStr = leftStr;
        this.context = context;
        this.flagArray = flagArray;
    }

    @Override
    public int getCount() {
        return leftStr.length;
    }

    @Override
    public Object getItem(int arg0) {
        return leftStr[arg0];
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        Holder holder = null;
        if (arg1 == null) {
            holder = new Holder();
            arg1 = LayoutInflater.from(context).inflate(R.layout.item_food_type, null);
            holder.left_list_item = (TextView) arg1.findViewById(R.id.text_type_item);
            arg1.setTag(holder);
        } else {
            holder = (Holder) arg1.getTag();
        }
        holder.updataView(arg0);
        return arg1;
    }

    private class Holder {
        private TextView left_list_item;

        public void updataView(final int position) {
            left_list_item.setText(leftStr[position]);
            if (flagArray[position]) {
                left_list_item.setBackgroundColor(Color.rgb(255, 255, 255));
            } else {
                left_list_item.setBackgroundColor(Color.TRANSPARENT);
            }
        }

    }
}
