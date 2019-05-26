package com.homework.getfood;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.homework.getfood.bean.OrderBean;
import com.homework.getfood.context.AppContext;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckActivity extends AppCompatActivity {

    private static OrderBean orderData;
    private OrderFoodAdapter orderFoodAdapter;

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
    private void initView(){
        orderPrice.setText("¥ " + orderData.getOrderPrice().toString());
        orderFoodAdapter = new OrderFoodAdapter(this,orderData.getOrderFoodList());
        orderFoodListView.setAdapter(orderFoodAdapter);
        surebuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().hide();
                AppContext.getCart().clear();
                MakeFragment.setPrice();
                final Intent intent = new Intent(CheckActivity.this,OrderActivity.class);
                OrderActivity.setOrderData(orderData);
                AppContext.updateOrder(orderData);
                waitLayout.setVisibility(View.GONE);
                payLayout.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        success(intent);
                    }
                }, 4000);
            }
        });
    }
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
    }
}
