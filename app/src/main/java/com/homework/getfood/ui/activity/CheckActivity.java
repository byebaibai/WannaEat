package com.homework.getfood.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.homework.getfood.ui.adapter.CouponAdapter;
import com.homework.getfood.ui.adapter.OrderFoodAdapter;
import com.homework.getfood.ui.fragment.MakeFragment;
import com.homework.getfood.R;
import com.homework.getfood.bean.OrderBean;
import com.homework.getfood.context.AppContext;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 确认订单界面Activity
 */
public class CheckActivity extends AppCompatActivity {

    private static OrderBean orderData;
    private OrderFoodAdapter orderFoodAdapter;
    private long time = 0;
    private CouponAdapter couponAdapter;
    @BindView(R.id.sureBuy)
    TextView surebuy;
    @BindView(R.id.orderPrice)
    TextView orderPrice;
    @BindView(R.id.foodListView)
    ListView orderFoodListView;
    @BindView(R.id.paying)
    ConstraintLayout payLayout;
    @BindView(R.id.waitCheck)
    ConstraintLayout waitLayout;
    @BindView(R.id.paySuccess)
    ConstraintLayout successLayout;
    @BindView(R.id.couponListView)
    ListView couponlistview;
    @BindView(R.id.originPrice)
    TextView originPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("确认订单");
        MainActivity.setViewPagerID(0);
        setContentView(R.layout.activity_check);
        ButterKnife.bind(this);
        initView();
        payLayout.setVisibility(View.GONE);
        successLayout.setVisibility(View.GONE);
        waitLayout.setVisibility(View.VISIBLE);
    }
    @SuppressLint("SetTextI18n")
    private void initView(){
        Integer price = orderData.getOrderPrice(); // 获得商品未打折价格
        couponAdapter = new CouponAdapter(this,AppContext.getCouponeList(price,orderData.getHasGroup()),price,orderData.getHasGroup());
        couponlistview.setAdapter(couponAdapter);
        orderFoodAdapter = new OrderFoodAdapter(this,orderData.getOrderFoodList());
        orderFoodListView.setAdapter(orderFoodAdapter);
        final Integer afterCoupon = price - couponAdapter.getMinusTotal(); // 商品打折后价格
        orderPrice.setText("¥ " + afterCoupon);
        originPrice.setText("¥ " + price);
        surebuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 确认购买操作
                getSupportActionBar().hide();
                AppContext.getCart().clear();
                MakeFragment.setPrice();
                final Intent intent = new Intent(CheckActivity.this, OrderActivity.class);
                orderData.setOrderActualPrice(afterCoupon);
                OrderActivity.setOrderData(orderData); // 将订单数据传给订单页面
                AppContext.updateOrder(orderData); // 更新订单数据
                waitLayout.setVisibility(View.GONE);
                payLayout.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() { //显示正在支付界面
                    @Override
                    public void run() {
                        success(intent);
                    }
                }, 4000);
            }
        });
    }

    /**
     * 展示完成订单动画
     * @param intent 目的界面
     */
    private void success(final Intent intent){
        payLayout.setVisibility(View.GONE);
        successLayout.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish(intent);
            }
        }, 1000);
    }
    public static void setOrderData(OrderBean orderData) {
        CheckActivity.orderData = orderData;
    }

    private void finish(Intent intent) {
        startActivity(intent);
    } // 跳转到订单详情页

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /* 点击的为返回键 */
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();// 退出方法
        }
        return true;
    }

    /**
     * 连按返回键退出应用
     */
    private void exit() {
        if (System.currentTimeMillis() - time > 2000) {
            time = System.currentTimeMillis();
            showToast("再点击一次退出应用程序");
        }else{
            System.out.println("??");
            this.finishAffinity();
            System.exit(0);
        }
    }
    @SuppressLint("WrongConstant")
    public void showToast(String text) {
        Toast.makeText(this, text, 2000).show();
    }
}
