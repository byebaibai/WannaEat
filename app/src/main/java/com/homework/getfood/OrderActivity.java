package com.homework.getfood;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.homework.getfood.bean.OrderBean;
import com.homework.getfood.context.AppContext;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends AppCompatActivity {
    private static OrderBean orderData;
    private OrderFoodAdapter orderFoodAdapter;
    private long time = 0;
    private CouponAdapter couponAdapter;
    @BindView(R.id.orderTime)
    TextView orderTime;
    @BindView(R.id.orderID)
    TextView orderID;
    @BindView(R.id.orderPrice)
    TextView orderPrice;
    @BindView(R.id.foodListView)
    ListView orderFoodListView;
    @BindView(R.id.continueBuy)
    TextView buyBuy;
    @BindView(R.id.getOrderList)
    TextView orderOrder;
    @BindView(R.id.couponListView)
    ListView couponlistview;
    @BindView(R.id.originPrice)
    TextView originPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        Integer price = orderData.getOrderPrice();
        couponAdapter = new CouponAdapter(this, AppContext.getCouponeList(price,orderData.getHasGroup()),price,orderData.getHasGroup());
        couponlistview.setAdapter(couponAdapter);
        orderTime.setText(orderData.getOrderTime());
        orderID.setText(orderData.getOrderID().toString());
        orderFoodAdapter = new OrderFoodAdapter(this,orderData.getOrderFoodList());
        orderFoodListView.setAdapter(orderFoodAdapter);
        Integer afterCoupon = price - couponAdapter.getMinusTotal();
        System.out.println(afterCoupon);
        orderPrice.setText("¥ " + afterCoupon);
        originPrice.setText("¥ " + price);
        buyBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderFragment of = MainActivity.getFragment_order();
                MakeFragment mf = MainActivity.getFragment_make();
                if (mf != null) mf.refreshCart();
                if (of != null) of.notifyData();
                else System.out.println("None");
                Intent intent = new Intent(OrderActivity.this,MainActivity.class);
                MainActivity.setViewPagerID(0);
                startActivity(intent);
            }
        });
        orderOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderFragment of = MainActivity.getFragment_order();
                MakeFragment mf = MainActivity.getFragment_make();
                if (mf != null) mf.refreshCart();
                if (of != null) of.notifyData();
                else System.out.println("None");
                Intent intent = new Intent(OrderActivity.this,MainActivity.class);
                MainActivity.setViewPagerID(1);
                startActivity(intent);
            }
        });
    }
    public static OrderBean getOrderData() {
        return orderData;
    }

    public static void setOrderData(OrderBean orderData) {
        OrderActivity.orderData = orderData;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /* 点击的为返回键 */
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();// 退出方法
        }
        return true;
    }

    private void exit() {
        if (System.currentTimeMillis() - time > 2000) {
            time = System.currentTimeMillis();
            showToast("再点击一次退出应用程序");
        }else{
            this.finishAffinity();
            System.exit(0);
        }
    }
    @SuppressLint("WrongConstant")
    public void showToast(String text) {
        Toast.makeText(this, text, 2000).show();
    }
}
