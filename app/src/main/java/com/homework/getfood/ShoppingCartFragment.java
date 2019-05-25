package com.homework.getfood;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homework.getfood.bean.FoodBean;
import com.homework.getfood.context.AppContext;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

public class ShoppingCartFragment extends Fragment implements View.OnClickListener {
    private boolean isScroll = true;
    private ListView mainlist;
    private PinnedHeaderListView morelist;
    private List<FoodBean> foodList; //保存购物车对象到List
    private TextView shoppingPrise; //购物车价格
    private TextView shoppingNum; // 购物车件数
    private TextView settlement; //去结算
    private FrameLayout cardLayout; //购物车View
    private LinearLayout cardShopLayout;
    private View bg_layout; //背景View
    private ImageView shopping_cart; //购物车Logo

    private TextView defaultText;
    //父布局
    private RelativeLayout parentLayout;
    private TextView noData;
    private ListView shoppingListView;

    private HashMap<String, FoodBean> cart = new HashMap<String, FoodBean>();
    @Override
    public void onClick(View v) {
        for (FoodBean fb : cart.values()){
            System.out.println("Name : " + fb.getName() + "  price : " + fb.getPrice().toString() +
                    " cartNum : " + fb.getCartNum().toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cart = AppContext.getCart();
        for (FoodBean fb : cart.values()){
            System.out.println("Name : " + fb.getName() + "  price : " + fb.getPrice().toString() +
                    " cartNum : " + fb.getCartNum().toString());
        }
        return inflater
                .inflate(R.layout.fragment_make, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {

    }
}
