package com.homework.getfood;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.homework.getfood.R;
import com.homework.getfood.bean.FoodBean;
import com.homework.getfood.context.AppContext;

import java.util.ArrayList;
import java.util.HashMap;

public class FoodListAdapter extends SectionedBaseAdapter {

    private Context mContext;
    private ArrayList<String> leftStr;
    private ArrayList<ArrayList<String>> rightStr;
    private HashMap<String,FoodBean> foodMap;
    private DialogDetail dialogDetail;
    public FoodListAdapter(Context context, ArrayList<String> leftStr, ArrayList<ArrayList<String>> rightStr, HashMap<String,FoodBean> foodmap) {
        this.foodMap = foodmap;
        this.mContext = context;
        this.leftStr = leftStr;
        this.rightStr = rightStr;
    }

    @Override
    public Object getItem(int section, int position) {
        return rightStr.get(section).get(position);
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return leftStr.size();
    }

    @Override
    public int getCountForSection(int section) {
        return rightStr.get(section).size();
    }

    @Override
    public View getItemView(final int section, final int position, View convertView, ViewGroup parent) {
        RelativeLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (RelativeLayout) inflator.inflate(R.layout.item_food, null);
        } else {
            layout = (RelativeLayout) convertView;
        }
        String name = rightStr.get(section).get(position);
        final FoodBean fb = foodMap.get(name);

        ((TextView) layout.findViewById(R.id.text_food_item)).setText(rightStr.get(section).get(position));
        ((TextView) layout.findViewById(R.id.text_food_value)).setText(fb.getPrice().toString() + "¥");
        ((ImageView) layout.findViewById(R.id.image_food_item)).setImageResource(fb.getIcon());
        //System.out.println(fb.getIcon());
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final Context context = arg0.getContext();
                dialogDetail = new DialogDetail(arg0.getContext(),fb.getIcon());
                System.out.println(fb.getIcon());

                dialogDetail.setInfo(fb.getName(),fb.getPrice().toString(),false);
                dialogDetail.setYesOnclickListener("确定", new DialogDetail.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        Integer num = dialogDetail.getInfo();
                        Toast.makeText(context,"点击了--确定--按钮 : " + num.toString(),Toast.LENGTH_LONG).show();
                        dialogDetail.dismiss();
                    }
                });
                dialogDetail.setNoOnclickListener("取消", new DialogDetail.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        Toast.makeText(context,"点击了--取消--按钮",Toast.LENGTH_LONG).show();
                        dialogDetail.dismiss();
                    }
                });
                dialogDetail.show();
            }
        });
        return layout;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.item_header, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        layout.setClickable(false);
        ((TextView) layout.findViewById(R.id.text_food_item)).setText(leftStr.get(section));
        return layout;
    }

}
