package com.homework.getfood;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import butterknife.ButterKnife;
import com.homework.getfood.FoodTypeListAdapter;
import com.homework.getfood.FoodListAdapter;
import com.homework.getfood.R;
import com.homework.getfood.PinnedHeaderListView;
import com.homework.getfood.bean.FoodBean;
import com.homework.getfood.context.AppContext;


import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link MakeFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link MakeFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class MakeFragment extends Fragment {

    @BindView(R.id.view_type_list)
    ListView foodtypeListView;

    @BindView(R.id.view_pinned_list)
    PinnedHeaderListView pinnedListView;

    private boolean isScroll = true;
    private FoodTypeListAdapter adapter;

    private ArrayList<String> leftStr;
    private ArrayList<Boolean> flagArray;
    private ArrayList<ArrayList<String>> rightStr;
    private ArrayList<String> temp;
    private View rootView;

    private AppContext globalFood;
    private ArrayList<FoodBean> foodData = new ArrayList<FoodBean>();
    private HashMap<String,FoodBean> foodMap;
    private int typeNum;
//    private OnFragmentInteractionListener mListener;
//
//    public MakeFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment MakeFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static MakeFragment newInstance(String param1, String param2) {
//        MakeFragment fragment = new MakeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void initData(){
        foodData = globalFood.getData();
        foodMap = globalFood.getMap();
        leftStr = new ArrayList<String>();
        rightStr = new ArrayList<ArrayList<String>>();
        flagArray = new ArrayList<Boolean>();
        boolean Flag = true;
        for (int i = 1;i<= globalFood.getTypeNum();i++){
            if (i == 1) flagArray.add(true);
            else flagArray.add(false);
            temp = new ArrayList<String>();
            for (int j = 0;j < foodData.size();j++){
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
        for (int i = 0; i<leftStr.size();i++){
            System.out.println(leftStr.get(i));
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initData();
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_make, container, false);
        pinnedListView = (PinnedHeaderListView) rootView.findViewById(R.id.view_pinned_list);
        ButterKnife.bind(this, rootView);
        final FoodListAdapter sectionedAdapter = new FoodListAdapter(rootView.getContext(), leftStr, rightStr, foodMap);
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

        pinnedListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (pinnedListView.getLastVisiblePosition() == (pinnedListView.getCount() - 1)) {
                            foodtypeListView.setSelection(ListView.FOCUS_DOWN);
                        }

                        // 判断滚动到顶部
                        if (pinnedListView.getFirstVisiblePosition() == 0) {
                            foodtypeListView.setSelection(0);
                        }

                        break;
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
//                            z = z + 3;
                            foodtypeListView.setSelection(z);
                        }
                        if (x == foodtypeListView.getFirstVisiblePosition()) {
//                            z = z - 1;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        globalFood = (AppContext) getActivity().getApplication();
        //textView = (TextView)getActivity().findViewById(R.id.fragment_make_id);
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
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
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
