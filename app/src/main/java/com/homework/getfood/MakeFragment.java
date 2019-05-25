package com.homework.getfood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.homework.getfood.bean.FoodBean;
import com.homework.getfood.bean.OrderBean;
import com.homework.getfood.context.AppContext;
import com.homework.getfood.util.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link MakeFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link MakeFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class MakeFragment extends Fragment implements View.OnClickListener, CartListener, UpdateListener {

    @BindView(R.id.view_type_list)
    ListView foodtypeListView;

    @BindView(R.id.view_pinned_list)
    PinnedHeaderListView pinnedListView;

    private boolean isScroll = true;
    private FoodTypeListAdapter adapter;

    private ArrayList<String> leftStr;
    private ArrayList<Boolean> flagArray;
    private ArrayList<ArrayList<String>> rightStr;
    private View rootView;

    private HashMap<String,FoodBean> foodMap;
    private HashMap<String,FoodBean> cartMap;
    private int typeNum;
    private ImageView shopping_cart;
    private TextView defaultText;
    private ListView shoppingListView;
    private FrameLayout cardLayout;

    private LinearLayout cardShopLayout;
    private View bg_layout;
    private TextView settlement;
    private List<FoodBean> productList;
    private CartAdapter cartAdapter;
    @SuppressLint("StaticFieldLeak")
    public static TextView shoppingPrice;

    @SuppressLint("StaticFieldLeak")
    public static TextView shoppingNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void initData(){
        ArrayList<FoodBean> foodData = AppContext.getData();
        foodMap = AppContext.getMap();
        leftStr = new ArrayList<String>();
        rightStr = new ArrayList<ArrayList<String>>();
        flagArray = new ArrayList<Boolean>();
        boolean Flag = true;
        for (int i = 1; i<= AppContext.getTypeNum(); i++){
            if (i == 1) flagArray.add(true);
            else flagArray.add(false);
            ArrayList<String> temp = new ArrayList<String>();
            for (int j = 0; j < foodData.size(); j++){
                FoodBean x = foodData.get(j);
                if (x.getTypeID() == i){
                    if(Flag){
                        leftStr.add(x.getType());
                        Flag = false;
                    }
                    temp.add(x.getName());
                }
                if (x.getTypeID() > i) break;
            }
            rightStr.add(temp);
            Flag = true;
        }
        bg_layout.setOnClickListener(this);
        settlement.setOnClickListener(this);
        shopping_cart.setOnClickListener(this);
    }
    private void initView(){
        shopping_cart = (ImageView)rootView.findViewById(R.id.shopping_cart);
        defaultText = (TextView)rootView.findViewById(R.id.defaultText);
        shoppingListView = (ListView)rootView.findViewById(R.id.shopproductListView);
        cardLayout = (FrameLayout)rootView.findViewById(R.id.cardLayout);
        cardShopLayout = (LinearLayout)rootView.findViewById(R.id.cardShopLayout);
        bg_layout =  (View) rootView.findViewById(R.id.bg_layout);
        settlement = (TextView)  rootView.findViewById(R.id.settlement);
        shoppingPrice = (TextView) rootView.findViewById(R.id.shoppingPrice);
        shoppingNum = (TextView) rootView.findViewById(R.id.shoppingNum);
        initData();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shopping_cart:
                if (AppContext.getCart().isEmpty() || AppContext.getCart() == null) {
                    defaultText.setVisibility(View.VISIBLE);
                } else {
                    defaultText.setVisibility(View.GONE);
                }

                if (cardLayout.getVisibility() == View.GONE) {
                    cardLayout.setVisibility(View.VISIBLE);
                    cardShopLayout.setVisibility(View.VISIBLE);
                    bg_layout.setVisibility(View.VISIBLE);

                } else {
                    cardLayout.setVisibility(View.GONE);
                    bg_layout.setVisibility(View.GONE);
                    cardShopLayout.setVisibility(View.GONE);
                }
                break;

            case R.id.settlement:
                System.out.println("Ah Buy!");
                if(AppContext.getCart().isEmpty() || AppContext.getCart() == null){
                    return;
                }else{
                    ArrayList<FoodBean> fb = new ArrayList<FoodBean>();
                    fb.addAll(AppContext.getCart().values());
                    OrderBean order = new OrderBean(6232,"2019-05-18 21:11",
                            424,-1,fb);
                    String s = JsonUtils.parseObjToJson(order);
                    System.out.println(s);
                    OrderBean temp = JsonUtils.parseJsonToObj(s,OrderBean.class);
                    String t = JsonUtils.parseObjToJson(temp);
                    System.out.println(t);
                }
                break;
            case R.id.bg_layout:
                cardLayout.setVisibility(View.GONE);
                bg_layout.setVisibility(View.GONE);
                cardShopLayout.setVisibility(View.GONE);
                break;
        }
    }
    @Override
    public void updateFood() {
        cartAdapter.updateData();
        cartAdapter.notifyDataSetChanged();
        //setPrice();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_make, container, false);
        initView();
        pinnedListView = rootView.findViewById(R.id.view_pinned_list);
        ButterKnife.bind(this, rootView);
        final FoodListAdapter sectionedAdapter = new FoodListAdapter(rootView.getContext(), leftStr, rightStr, foodMap);
        sectionedAdapter.setCallBackListener(this);
        pinnedListView.setAdapter(sectionedAdapter);
        adapter = new FoodTypeListAdapter(rootView.getContext(), leftStr, flagArray);
        foodtypeListView.setAdapter(adapter);
        foodtypeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isScroll = false;

                for (int i = 0; i < leftStr.size(); i++) {
                    if (i == position) {
                        flagArray.set(i,true);
                    } else {
                        flagArray.set(i,false);
                    }
                }
                adapter.notifyDataSetChanged();
                int rightSection = 0;
                for (int i = 0; i < position; i++) {
                    rightSection += sectionedAdapter.getCountForSection(i) + 1;
                }
                pinnedListView.setSelection(rightSection);
            }
        });
        cartAdapter = new CartAdapter(getActivity(),AppContext.getCart());
        shoppingListView.setAdapter(cartAdapter);
        cartAdapter.setCartListener(this);

        pinnedListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 当不滚动时
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {// 判断滚动到底部
                    if (pinnedListView.getLastVisiblePosition() == (pinnedListView.getCount() - 1)) {
                        foodtypeListView.setSelection(ListView.FOCUS_DOWN);
                    }

                    // 判断滚动到顶部
                    if (pinnedListView.getFirstVisiblePosition() == 0) {
                        foodtypeListView.setSelection(0);
                    }
                }
            }

            int y = 0;
            int x = 0;
            int z = 0;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScroll) {
                    for (int i = 0; i < rightStr.size(); i++) {
                        if (i == sectionedAdapter.getSectionForPosition(pinnedListView.getFirstVisiblePosition())) {
                            flagArray.set(i,true);
                            x = i;
                        } else {
                            flagArray.set(i,false);
                        }
                    }
                    if (x != y) {
                        adapter.notifyDataSetChanged();
                        y = x;
                        if (y == foodtypeListView.getLastVisiblePosition()) {
                            foodtypeListView.setSelection(z);
                        }
                        if (x == foodtypeListView.getFirstVisiblePosition()) {
                            foodtypeListView.setSelection(z);
                        }
                        if (firstVisibleItem + visibleItemCount == totalItemCount - 1) {
                            foodtypeListView.setSelection(ListView.FOCUS_DOWN);
                        }
                    }
                } else {
                    isScroll = true;
                }
            }
        });
        return rootView;
    }

    @Override
    public void onRemoveFood(FoodBean product) {

        AppContext.getCart().remove(product.getName());
        updateFood();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //textView = (TextView)getActivity().findViewById(R.id.fragment_make_id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
//
    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
        ButterKnife.bind(this, rootView);
    }

    @SuppressLint("SetTextI18n")
    static public void setPrice() {
        Integer priceSum = 0, foodSum = 0;
        HashMap<String, FoodBean> cart = AppContext.getCart();
        for (FoodBean item : cart.values()){
            foodSum = foodSum + item.getCartNum();
            priceSum = priceSum + item.getCartNum() * item.getPrice();
        }

        if (foodSum > 0){
            shoppingNum.setVisibility(View.VISIBLE);
        }else{
            shoppingNum.setVisibility(View.GONE);
        }
        if (priceSum > 0){
            shoppingPrice.setVisibility(View.VISIBLE);
        }else{
            shoppingPrice.setVisibility(View.GONE);
        }
        shoppingPrice.setText(priceSum.toString() + "  ¥");
        shoppingNum.setText(foodSum.toString());
    }
}
