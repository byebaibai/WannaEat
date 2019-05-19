package com.homework.getfood;

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

    private String[] leftStr = new String[]{"面食类", "盖饭", "寿司", "烧烤", "酒水", "凉菜", "小吃", "粥", "休闲"};
    private boolean[] flagArray = {true, false, false, false, false, false, false, false, false};
    private String[][] rightStr = new String[][]{{"热干面", "臊子面", "烩面"},
            {"番茄鸡蛋", "红烧排骨", "农家小炒肉"},
            {"芝士", "丑小丫", "金枪鱼"}, {"羊肉串", "烤鸡翅", "烤羊排"}, {"长城干红", "燕京鲜啤", "青岛鲜啤"},
            {"拌粉丝", "大拌菜", "菠菜花生"}, {"小食组", "紫薯"},
            {"小米粥", "大米粥", "南瓜粥", "玉米粥", "紫米粥"}, {"儿童小汽车", "悠悠球", "熊大", " 熊二", "光头强"}
    };
    
    private View rootView;
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_make, container, false);
        pinnedListView = (PinnedHeaderListView) rootView.findViewById(R.id.view_pinned_list);
        ButterKnife.bind(this, rootView);
        final FoodListAdapter sectionedAdapter = new FoodListAdapter(rootView.getContext(), leftStr, rightStr);
        pinnedListView.setAdapter(sectionedAdapter);
        adapter = new FoodTypeListAdapter(rootView.getContext(), leftStr, flagArray);
        foodtypeListView.setAdapter(adapter);
        foodtypeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isScroll = false;

                for (int i = 0; i < leftStr.length; i++) {
                    if (i == position) {
                        flagArray[i] = true;
                    } else {
                        flagArray[i] = false;
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
                    for (int i = 0; i < rightStr.length; i++) {
                        if (i == sectionedAdapter.getSectionForPosition(pinnedListView.getFirstVisiblePosition())) {
                            flagArray[i] = true;
                            x = i;
                        } else {
                            flagArray[i] = false;
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
        //textView = (TextView)getActivity().findViewById(R.id.fragment_make_id);
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
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
