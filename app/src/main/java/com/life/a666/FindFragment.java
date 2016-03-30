package com.life.a666;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends Fragment  {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab01, container, false);
        /**在Fragment 中的组件 可以通过 getView（）.findViewById（R.id.xxx）获取到。
         * 但是注意： 可以在onStart（）方法中初始化组件，而不能在 onCreate（）方法中！
         * (2) 在拥有Fragment布局的Activity中，可以直接使用 findViewById（R.id.xxx）获取到fragment中的组件
         * */
        initView(view);
        //getView()会报错的 要用view。为什么啊？ listView = (ListView) getView().findViewById(R.id.listView);
        //getView().findViewById(R.id.btnPrivateChat).setOnClickListener(new View.OnClickListener() {
//        Toast.makeText(getActivity(), "onCreateView", Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        useListView();
    }

    public void initView(View view) {

    }
    /**
     * 使用useListView
     */
    private void useListView() {
    }

}

