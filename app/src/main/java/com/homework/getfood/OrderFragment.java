package com.homework.getfood;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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
    private OrderAdapter orderAdapter;

    private View rootView;
    private ArrayList<OrderBean> orderBeanArrayList;

    @BindView(R.id.noOrder)
    ConstraintLayout nothingView;

    @BindView(R.id.orderListView)
    ListView orderListview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this,rootView);
        orderAdapter = new OrderAdapter(getActivity());
        refreshView();
        orderListview.setAdapter(orderAdapter);
        return rootView;
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
}
