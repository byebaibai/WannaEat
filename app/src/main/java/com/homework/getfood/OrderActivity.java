package com.homework.getfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.homework.getfood.bean.OrderBean;
import com.homework.getfood.util.StringFetcher;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends AppCompatActivity {
    private static OrderBean orderData;
    private OrderFoodAdapter orderFoodAdapter;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        orderTime.setText(orderData.getOrderTime());
        orderID.setText(orderData.getOrderID().toString());
        orderPrice.setText("¥ " + orderData.getOrderPrice().toString());
        orderFoodAdapter = new OrderFoodAdapter(this,orderData.getOrderFoodList());
        orderFoodListView.setAdapter(orderFoodAdapter);
        buyBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderFragment of = MainActivity.getFragment_order();
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
}
