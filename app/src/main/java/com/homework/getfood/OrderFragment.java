package com.homework.getfood;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.homework.getfood.bean.FoodBean;
import com.homework.getfood.bean.OrderBean;
import com.homework.getfood.context.AppContext;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link OrderFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link OrderFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class OrderFragment extends Fragment {
    private boolean isScroll = true;
    public OrderAdapter orderAdapter;

    private View rootView;
    private ArrayList<OrderBean> orderBeanArrayList;

    @BindView(R.id.noOrder)
    ConstraintLayout nothingView;

    @BindView(R.id.orderListView)
    SwipeMenuListView orderListview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this,rootView);
        initView();
        refreshView();
        System.out.println("onCreateView");
        MainActivity.setFragment_order(this);
        return rootView;
    }
    private void initView(){
        orderAdapter = new OrderAdapter(getActivity());
        orderListview.setAdapter(orderAdapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        AppContext.getContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(251,
                        111, 100)));
                deleteItem.setWidth(dp2px(60));
                deleteItem.setIcon(R.drawable.garbage);
                menu.addMenuItem(deleteItem);
            }
        };
        orderListview.setMenuCreator(creator);
        orderListview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if (index == 0) {
                    System.out.println("Delete");
                    AppContext.getOrderBeanArrayList().remove(position);
                    orderAdapter.notifyDataSetChanged();
                    AppContext.updateRemove();
                    refreshView();
                }
                return false;
            }
        });
        orderListview.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    private void refreshView(){
        if (AppContext.getOrderBeanArrayList() == null || AppContext.getOrderBeanArrayList().size() == 0){
            nothingView.setVisibility(View.VISIBLE);
            orderListview.setVisibility(View.GONE);
        }else{
            nothingView.setVisibility(View.GONE);
            orderListview.setVisibility(View.VISIBLE);
        }
    }

    private int dp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                getResources().getDisplayMetrics());
    }
    //另一种将dp转换为px的方法
    private int dp2px(float value){
        final float scale = getResources().getDisplayMetrics().density;
        return (int)(value*scale + 0.5f);
    }

    public void notifyData() {
        refreshView();
        orderAdapter.notifyDataSetChanged();
    }
}
