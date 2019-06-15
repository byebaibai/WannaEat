package com.homework.getfood.ui.adapter;

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

import com.homework.getfood.R;
import com.homework.getfood.bean.FoodBean;
import com.homework.getfood.context.AppContext;
import com.homework.getfood.ui.dialog.DetailDialog;
import com.homework.getfood.ui.fragment.MakeFragment;
import com.homework.getfood.util.IconFetcher;
import com.homework.getfood.util.UpdateListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 食物ListView的Adapter
 */
public class SectionedBaseFoodListAdapter extends SectionedBaseAdapter {

    private Context mContext;
    private ArrayList<String> leftStr;
    private ArrayList<ArrayList<String>> rightStr;
    private HashMap<String,FoodBean> foodMap;
    private HashMap<String,FoodBean> cartMap;
    private DetailDialog detailDialog;

    private UpdateListener callBackListener;

    public void setCallBackListener(UpdateListener callBackListener) {
        this.callBackListener = callBackListener;
    }
    public SectionedBaseFoodListAdapter(Context context, ArrayList<String> leftStr, ArrayList<ArrayList<String>> rightStr, HashMap<String,FoodBean> foodmap) {
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

    @SuppressLint("SetTextI18n")
    @Override
    public View getItemView(final int section, final int position, View convertView, ViewGroup parent) {
        RelativeLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (RelativeLayout) inflator.inflate(R.layout.adapter_food_item, null);
        } else {
            layout = (RelativeLayout) convertView;
        }
        String name = rightStr.get(section).get(position);
        final FoodBean fb = foodMap.get(name);
        final int iconID = IconFetcher.getResId(fb.getIcon(),R.drawable.class);
        ((TextView) layout.findViewById(R.id.text_food_item)).setText(rightStr.get(section).get(position));
        ((TextView) layout.findViewById(R.id.text_food_value)).setText(fb.getPrice().toString() + "¥");
        ((ImageView) layout.findViewById(R.id.image_food_item)).setImageResource(iconID);
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final Context context = arg0.getContext();
                detailDialog = new DetailDialog(arg0.getContext(),iconID);
                detailDialog.setInfo(fb.getName(),fb.getPrice().toString(),fb.getSpicy(),fb.getDetail());
                detailDialog.setYesOnclickListener("确定", new DetailDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() { // 确认将商品加入购物车
                        Integer num = detailDialog.getInfo();
                        if (num == 0) num ++;
                        FoodBean newItem = new FoodBean(fb.getId(),fb.getTypeID(),fb.getType(),fb.getName(),fb.getPrice(),fb.getIcon(),num,fb.getGroup(),fb.getDetail());
                        if (fb.getSpicy()) newItem.setName(fb.getName() + DetailDialog.getSpicy()); // 如果食物是辣的，则设置其辣度
                        if (!cartMap.containsKey(newItem.getName())) cartMap.put(newItem.getName(),newItem);
                        else{
                            FoodBean temp = cartMap.get(newItem.getName());
                            assert temp != null;
                            temp.updateCartNum(num);
                            AppContext.getCart().remove(newItem.getName()); // 更新本地购物车数据
                            AppContext.getCart().put(newItem.getName(),temp);
                        }
                        if (callBackListener != null) {
                            callBackListener.updateFood();
                        }
                        detailDialog.dismiss();
                        setPrice();
                    }
                });
                detailDialog.setNoOnclickListener("取消", new DetailDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        detailDialog.dismiss();
                    }
                });
                detailDialog.show();
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
            layout = (LinearLayout) inflator.inflate(R.layout.adapter_header_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        layout.setClickable(false);
        ((TextView) layout.findViewById(R.id.text_food_item)).setText(leftStr.get(section));
        return layout;
    }

    /**
     * 设置购物车价格以及购物车显示
     */
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
