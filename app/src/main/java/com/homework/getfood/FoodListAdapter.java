package com.homework.getfood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.homework.getfood.bean.FoodBean;
import com.homework.getfood.context.AppContext;
import com.homework.getfood.util.IconFetcher;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class FoodListAdapter extends SectionedBaseAdapter {

    private Context mContext;
    private ArrayList<String> leftStr;
    private ArrayList<ArrayList<String>> rightStr;
    private HashMap<String,FoodBean> foodMap;
    private HashMap<String,FoodBean> cartMap;
    private DialogDetail dialogDetail;

    private UpdateListener callBackListener;

    public void setCallBackListener(UpdateListener callBackListener) {
        this.callBackListener = callBackListener;
    }
    public FoodListAdapter(Context context, ArrayList<String> leftStr, ArrayList<ArrayList<String>> rightStr, HashMap<String,FoodBean> foodmap) {
        this.foodMap = foodmap;
        this.mContext = context;
        this.leftStr = leftStr;
        this.rightStr = rightStr;
        cartMap = AppContext.getCart();
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
        final int iconID = IconFetcher.getResId(fb.getIcon(),R.drawable.class);
        ((TextView) layout.findViewById(R.id.text_food_item)).setText(rightStr.get(section).get(position));
        ((TextView) layout.findViewById(R.id.text_food_value)).setText(fb.getPrice().toString() + "¥");
        ((ImageView) layout.findViewById(R.id.image_food_item)).setImageResource(iconID);
        //System.out.println(fb.getIcon());
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final Context context = arg0.getContext();
                dialogDetail = new DialogDetail(arg0.getContext(),iconID);
                System.out.println(fb.getIcon());
                dialogDetail.setInfo(fb.getName(),fb.getPrice().toString(),false);
                dialogDetail.setYesOnclickListener("确定", new DialogDetail.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        Integer num = dialogDetail.getInfo();
                        if (num == 0) num ++;
                        FoodBean newItem = new FoodBean(fb.getId(),fb.getTypeID(),fb.getType(),fb.getName(),fb.getPrice(),fb.getIcon(),num);
                        if (!cartMap.containsKey(newItem.getName()))cartMap.put(newItem.getName(),newItem);
                        else{
                            FoodBean temp = cartMap.get(newItem.getName());
                            assert temp != null;
                            temp.updateCartNum(num);
                            AppContext.getCart().remove(newItem.getName());
                            AppContext.getCart().put(newItem.getName(),temp);
                        }
                        if (callBackListener != null) {
                            callBackListener.updateFood();
                        } else {
                        }
                        dialogDetail.dismiss();
                        setPrice();
                    }
                });
                dialogDetail.setNoOnclickListener("取消", new DialogDetail.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
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

    @SuppressLint("SetTextI18n")
    private void setPrice() {
        Integer priceSum = 0, foodSum = 0;
        HashMap<String, FoodBean> cart = AppContext.getCart();
        for (FoodBean item : cart.values()){
            foodSum = foodSum + item.getCartNum();
            priceSum = priceSum + item.getCartNum() * item.getPrice();
        }

        if (foodSum > 0){
            MakeFragment.shoppingNum.setVisibility(View.VISIBLE);
        }else{
            MakeFragment.shoppingNum.setVisibility(View.GONE);
        }
        if (priceSum > 0){
            MakeFragment.shoppingPrice.setVisibility(View.VISIBLE);
        }else{
            MakeFragment.shoppingPrice.setVisibility(View.GONE);
        }
        MakeFragment.shoppingPrice.setText(priceSum.toString() + "  ¥");
        MakeFragment.shoppingNum.setText(foodSum.toString());
    }
}
