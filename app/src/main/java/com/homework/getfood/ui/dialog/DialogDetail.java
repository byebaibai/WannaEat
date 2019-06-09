package com.homework.getfood.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import com.homework.getfood.R;

import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;

/**
 * 商品确认 单元
 */
public class DialogDetail extends Dialog {
    final private static String[] spicyDegreeString = new String[]{"(微辣)","(中辣)", "(猛辣)"}; //辣度
    private static int spicyDegreeID = 0; //辣度Id
    private Button yesButton;
    private Button noButton;
    private TextView foodName;
    private ImageView foodImage;
    private TextView foodPrice;
    private TextView foodNumber;
    private Integer totalPrice; //食物总价
    private Integer onePrice; //食物单价
    private RadioRealButtonGroup canSpicyButtonGroup; //辣度选择按钮组
    private String foodNameStr; //从外界设置的title文本
    private String foodPriceStr; //食物价格
    private onNoOnclickListener noOnclickListener;
    private onYesOnclickListener yesOnclickListener;
    private boolean canSpicy; //食物是否啦
    private ImageButton addButton;
    private ImageButton minusButton;
    private Integer foodNum = 1; //食物数目
    private int imageID; //食物图片ID
    private TextView foodDetail; //食物描述
    private String detailString;
    private Context mContext;
    public DialogDetail(Context context,int imageId) {
        super(context, R.style.DetailDialog);
        spicyDegreeID = 0;
        mContext = context;
        imageID = imageId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_dialog_select);
        setCanceledOnTouchOutside(false);

        initView();

        initData();

        initEvent();
    }

    private void initView(){
        yesButton = (Button) findViewById(R.id.yes);
        noButton = (Button) findViewById(R.id.no);

        foodNumber = (TextView) findViewById(R.id.foodNum);

        foodName = (TextView) findViewById(R.id.foodName);
        foodPrice = (TextView) findViewById(R.id.foodPrice);

        foodImage = (ImageView) findViewById(R.id.foodImage);

        canSpicyButtonGroup = (RadioRealButtonGroup) findViewById(R.id.canSpicyGroup);
        foodDetail = (TextView) findViewById(R.id.text_group);
        addButton = (ImageButton) findViewById(R.id.addButton);
        minusButton = (ImageButton) findViewById(R.id.minusButton);

        canSpicyButtonGroup.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(RadioRealButton button, int position) {
                spicyDegreeID = position;
            }
        });

        if (!canSpicy){
            canSpicyButtonGroup.setVisibility(View.GONE);
        }else{
            canSpicyButtonGroup.setVisibility(View.VISIBLE);
        }

        foodImage.setImageResource(imageID);
    }

    private void initEvent(){
        // 设置确定按钮被点击后，向外界提供监听
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    System.out.println("Yes!");
                    yesOnclickListener.onYesClick();
                }
            }
        });
        // 设置取消按钮被点击后，向外界提供监听
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
            public void onClick(View v) { // 增加食物
                String num = foodNumber.getText().toString();
                Integer n;
                if(num.length() >= 1) n = Integer.parseInt(num);
                else n = 0;
                if (n < 1000) n++;
                totalPrice = n * onePrice;
                foodNum = n;
                foodPrice.setText(totalPrice.toString() + "¥");
                foodNumber.setText(n.toString());
            }
        });
        minusButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) { // 减少食物
                String num = foodNumber.getText().toString();
                Integer n;
                if(num.length() >= 1) n = Integer.parseInt(num);
                else n = 0;
                if (n > 1) n--;
                totalPrice = n * onePrice;
                foodNum = n;
                foodPrice.setText(totalPrice.toString() + "¥");
                foodNumber.setText(n.toString());
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
        if (detailString != null){
            foodDetail.setText(detailString);
        }
    }
    public void setName(String name){
        foodNameStr = name;
    }
    public void setInfo(String name, String price, boolean spicy,String detail){ // 设置数据
        onePrice = Integer.parseInt(price);
        foodPriceStr = price + "¥";
        foodNameStr = name;
        canSpicy = spicy;
        this.detailString = detail;
    }

    /**
     * 得到食物数目
     * @return 食物数目
     */
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
        }
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 得到辣度文本
     * @return 辣度文本
     */
    public static String getSpicy(){
        return spicyDegreeString[spicyDegreeID];
    }


    public void setYesOnclickListener(String str, onYesOnclickListener onYesOnclickListener) {
        if (str != null) {
        }
        this.yesOnclickListener = onYesOnclickListener;
    }

}
