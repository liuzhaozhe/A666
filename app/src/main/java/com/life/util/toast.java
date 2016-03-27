package com.life.util;

import android.content.Context;
import android.widget.Toast;

import com.life.a666.R;

/**
 * Created by 惟我独尊 on 2015/12/13.
 */
public class toast {
    public static  void ShowText(String string, Context context){
        Toast.makeText(context, string,Toast.LENGTH_SHORT).show();

    }
}
