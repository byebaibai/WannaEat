package com.homework.getfood.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.homework.getfood.R;
import com.homework.getfood.context.AppContext;
import com.homework.getfood.ui.activity.ActivityMain;
import com.homework.getfood.ui.adapter.AdapterOrder;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 历史订单Fragment
 */
public class FragmentOrder extends Fragment {
    public AdapterOrder adapterOrder;

    private View rootView;

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
        ActivityMain.setFragment_order(this);
        return rootView;
    }
    private void initView(){
        adapterOrder = new AdapterOrder(getActivity());
        orderListview.setAdapter(adapterOrder);
        addSwipeMenu();
    }

    /**
     * 设置单个订单向左滑动删除
     */
    private void addSwipeMenu(){
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        AppContext.getContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(251,
                        111, 100)));
                deleteItem.setWidth(dp2px(60));
                //deleteItem.setIcon(R.drawable.garbage);
                deleteItem.setTitle("删除订单");
                deleteItem.setTitleSize(12);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        orderListview.setMenuCreator(creator);
        orderListview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if (index == 0) {
                    AppContext.getOrderBeanArrayList().remove(position); //从订单列表上删除这一订单
                    adapterOrder.notifyDataSetChanged();
                    AppContext.updateRemove(); //更新本地的订单数据
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

    /**
     * 刷新订单ListView页面
     */
    private void refreshView(){
        if (AppContext.getOrderBeanArrayList() == null || AppContext.getOrderBeanArrayList().size() == 0){
            nothingView.setVisibility(View.VISIBLE);
            orderListview.setVisibility(View.GONE);
        }else{
            nothingView.setVisibility(View.GONE);
            orderListview.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 将dp转换为px
     * @param value 输入dp的值
     * @return dp转换后px的值
     */
    private int dp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                getResources().getDisplayMetrics());
    }

    /**
     * 通知订单ListView中的数据
     */
    public void notifyData() {
        refreshView();
        adapterOrder.notifyDataSetChanged();
    }
}
