package com.homework.getfood;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.View;

public class DialogDetail extends Dialog {
    private Button yesButton;
    private Button noButton;
    private TextView foodName;
    private ImageView foodImage;
    private TextView foodPrice;
    private EditText foodNumEditor;
    private ImageButton addButton;
    private ImageButton minusButton;
    private Integer totalPrice;
    private Integer onePrice;
    private RadioGroup canSpicyButtonGroup;
    private String foodNameStr;//从外界设置的title文本
    private String messageStr;//从外界设置的消息文本
    private String foodPriceStr;
    private onNoOnclickListener noOnclickListener;
    private onYesOnclickListener yesOnclickListener;
    private boolean canSpicy;
    private Integer foodNum = 1;
    private String yesStr, noStr;

    public DialogDetail(Context context) {
        super(context, R.style.DetailDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select);
        setCanceledOnTouchOutside(false);

        initView();

        initData();

        initEvent();
    }

    private void initView(){
        yesButton = (Button) findViewById(R.id.yes);
        noButton = (Button) findViewById(R.id.no);

        addButton = (ImageButton) findViewById(R.id.addButton);
        minusButton = (ImageButton) findViewById(R.id.minusButton);

        foodNumEditor = (EditText) findViewById(R.id.foodNum);

        foodName = (TextView) findViewById(R.id.foodName);
        foodPrice = (TextView) findViewById(R.id.foodPrice);

        foodImage = (ImageView) findViewById(R.id.foodImage);

        canSpicyButtonGroup = (RadioGroup) findViewById(R.id.canSpicyGroup);

        if (canSpicy == false){
            canSpicyButtonGroup.setVisibility(View.GONE);
        }else{
            canSpicyButtonGroup.setVisibility(View.VISIBLE);
        }
    }

    private void initEvent(){
        foodNumEditor.setKeyListener(DigitsKeyListener.getInstance("012346789"));

        foodNumEditor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String num = foodNumEditor.getText().toString();
                Integer n;
                if(num.length() >= 1) n = Integer.parseInt(num);
                else n = 0;
                totalPrice = n * onePrice;
                foodNum = n;
                foodPrice.setText(totalPrice.toString() + "¥");
                foodNumEditor.setText(n.toString());
                return false;
            }
        });

        //设置确定按钮被点击后，向外界提供监听
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    System.out.println("Yes!");
                    yesOnclickListener.onYesClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnclickListener != null) {
                    System.out.println("No");
                    noOnclickListener.onNoClick();
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String num = foodNumEditor.getText().toString();
                Integer n;
                if(num.length() >= 1) n = Integer.parseInt(num);
                else n = 0;
                if (n < 1000) n++;
                totalPrice = n * onePrice;
                foodNum = n;
                foodPrice.setText(totalPrice.toString() + "¥");
                foodNumEditor.setText(n.toString());
            }
        });
        minusButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String num = foodNumEditor.getText().toString();
                Integer n;
                if(num.length() >= 1) n = Integer.parseInt(num);
                else n = 0;
                if (n > 1) n--;
                totalPrice = n * onePrice;
                foodNum = n;
                foodPrice.setText(totalPrice.toString() + "¥");
                foodNumEditor.setText(n.toString());
            }
        });
    }
    private void initData(){
        if (foodNameStr != null){
            foodName.setText(foodNameStr);
        }
        if (foodPriceStr != null){
            foodPrice.setText(foodPriceStr);
        }
    }
    public void setName(String name){
        foodNameStr = name;
    }
    public void setInfo(String name, String price, boolean spicy){
        onePrice = Integer.parseInt(price);
        foodPriceStr = price + "¥";
        foodNameStr = name;
        canSpicy = spicy;
    }
    public Integer getInfo(){
        return foodNum;
    }
    public interface onYesOnclickListener {
        public void onYesClick();
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }

    public void setNoOnclickListener(String str, onNoOnclickListener onNoOnclickListener) {
        if (str != null) {
            noStr = str;
        }
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param str
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener(String str, onYesOnclickListener onYesOnclickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.yesOnclickListener = onYesOnclickListener;
    }

}
