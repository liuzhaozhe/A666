package com.life.a666;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

//import com.life.a666.base.ColorActivity;
import com.life.application.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingFragment extends Fragment implements View.OnClickListener {


    ImageView imvQRCode;
    ImageView ImageView2;
    TextView textView1;
    TextView textView2;
    ListView listViewVideo;
    SimpleAdapter simpleAdapter;
    List<Map<String, Object>> list_data;
    LinearLayout set_theme;
    LinearLayout personalInformation;

    int SET_COLOR_RESULT = 23;
    //color集合
    private int[] colors = {
            R.color.system,
            R.color.aqua, R.color.blueviolet, R.color.coral,
            R.color.darkblue, R.color.darkcyan, R.color.ghostwhite,
            R.color.indianred, R.color.lightcyan, R.color.lightpink,
            R.color.orchid, R.color.slategray,
            R.color.tan, R.color.violet, R.color.yellow, 0,
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab04, container, false);
        init(view);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void init(View view) {

        set_theme = (LinearLayout) view.findViewById(R.id.set_theme);
        personalInformation = (LinearLayout) view.findViewById(R.id.personalInformation);

        set_theme.setOnClickListener(this);
        personalInformation.setOnClickListener(this);
    }

    private void showDialog() {
        //随机生成dot图标
        int[] draw = {
                R.drawable.color_system,
                R.drawable.color_aqua, R.drawable.color_blueviolet, R.drawable.color_coral,
                R.drawable.color_darkblue, R.drawable.color_darkcyan, R.drawable.color_ghostwhite,
                R.drawable.color_indianred, R.drawable.color_lightcyan, R.drawable.color_lightpink,
                R.drawable.color_orchid, R.drawable.color_slategray,
                R.drawable.color_tan, R.drawable.color_violet, R.drawable.color_yellow, 0,
        };

        String[] color = {
                "默认", "浅绿色", "蓝紫罗兰", "珊瑚", "暗蓝色", "暗青色", "幽灵白", "印度红", "淡青色", "浅粉红", "兰花紫", "灰石色", "茶色", "紫罗兰", "纯黄", "自定义"
        };
        //生成动态数组，加入数据
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
        for (int i = 0; i < draw.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("color", color[i]);
            map.put("image", draw[i]);
            arrayList.add(map);
        }

        new AlertDialog.Builder(getActivity())
                .setTitle("选择主题")
                .setAdapter(new SimpleAdapter(
                        getActivity(),
                        arrayList,
                        R.layout.layout_settings_item,
                        new String[]{"color", "image"},
                        new int[]{R.id.color, R.id.image}
                ), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        暂时不考虑自定义颜色
//                        change = true;
                        if (which == (colors.length - 1)) {
//                            自定义颜色
//                            startActivityForResult(new Intent(getActivity(), ColorActivity.class), SET_COLOR_RESULT);
                        } else {
                            MyApplication.getInstance().setPrimaryColor(getResources().getColor(colors[which]));
//                        重新创建改变颜色
                            getActivity().recreate();
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .create().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_theme:
                showDialog();
                break;
            case R.id.personalInformation:
                startActivity(new Intent(getActivity(),LoginedUserInformationActivity.class));
                break;
//            case R.id.personalInformation:
//                break;
        }
    }
}
