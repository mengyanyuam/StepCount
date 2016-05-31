package com.example.mengyanyuan8023.accelerometersensortest.Util;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mengyanyuan8023 on 2016/5/30.
 */
public class DateSave {

    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    public void stepSave(Context context){

        editor = context.getSharedPreferences("StepDate",
                Context.MODE_PRIVATE).edit();

        editor.putInt("lastStep",StepDetector.CURRENT_SETP);
        editor.commit();
    }

    public void stepClear(Context context){
        editor = context.getSharedPreferences("StepDate",
                Context.MODE_PRIVATE).edit();
        editor.putInt("lastStep",0);
        editor.commit();
    }

    public static void saveSelf(Context context){
        editor = context.getSharedPreferences("StepDate",
                Context.MODE_PRIVATE).edit();
        if(pref.getInt("lastStep",0) < StepDetector.CURRENT_SETP){
            editor.putInt("lastStep",StepDetector.CURRENT_SETP);
            editor.commit();
        }
    }

    public int getStepNumber(Context context){
        pref = context.getSharedPreferences("StepDate",Context.MODE_PRIVATE);
        return pref.getInt("lastStep",0);
    }

    public int getDay(Context context){
        pref = context.getSharedPreferences("StepDate",Context.MODE_PRIVATE);
        return pref.getInt("time",-1);
    }
}
