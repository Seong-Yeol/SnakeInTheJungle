package com.example.nam.snakeinthejungle;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.example.nam.snakeinthejungle.R;

import java.util.Hashtable;

/**
 * Created by Nam on 2016-04-28.
 * 사용되는 BitMap 자료들을 담고있는 콘테이너
 */
public class BitMapContainer extends View {

    private Hashtable<String,Bitmap> mHt;

    public BitMapContainer(Context context){
        super(context);
        mHt = new Hashtable<>();
        Resources res = getResources();

        BitmapDrawable bd = (BitmapDrawable)res.getDrawable(R.drawable.background);
        mHt.put("background", bd.getBitmap());
        bd = (BitmapDrawable)res.getDrawable(R.drawable.head1);
        mHt.put("head1", bd.getBitmap());
        bd = (BitmapDrawable)res.getDrawable(R.drawable.head2);
        mHt.put("head2", bd.getBitmap());
        bd = (BitmapDrawable)res.getDrawable(R.drawable.head3);
        mHt.put("head3", bd.getBitmap());
        bd = (BitmapDrawable)res.getDrawable(R.drawable.head4);
        mHt.put("head4", bd.getBitmap());

        bd = (BitmapDrawable)res.getDrawable(R.drawable.body1);
        mHt.put("body1", bd.getBitmap());
        bd = (BitmapDrawable)res.getDrawable(R.drawable.body2);
        mHt.put("body2", bd.getBitmap());

        bd = (BitmapDrawable)res.getDrawable(R.drawable.c_body1);
        mHt.put("c_body1", bd.getBitmap());
        bd = (BitmapDrawable)res.getDrawable(R.drawable.c_body2);
        mHt.put("c_body2", bd.getBitmap());
        bd = (BitmapDrawable)res.getDrawable(R.drawable.c_body3);
        mHt.put("c_body3", bd.getBitmap());
        bd = (BitmapDrawable)res.getDrawable(R.drawable.c_body4);
        mHt.put("c_body4", bd.getBitmap());

        bd = (BitmapDrawable)res.getDrawable(R.drawable.tail1);
        mHt.put("tail1", bd.getBitmap());
        bd = (BitmapDrawable)res.getDrawable(R.drawable.tail2);
        mHt.put("tail2", bd.getBitmap());
        bd = (BitmapDrawable)res.getDrawable(R.drawable.tail3);
        mHt.put("tail3", bd.getBitmap());
        bd = (BitmapDrawable)res.getDrawable(R.drawable.tail4);
        mHt.put("tail4", bd.getBitmap());

        bd = (BitmapDrawable)res.getDrawable(R.drawable.empty);
        mHt.put("empty", bd.getBitmap());

        bd = (BitmapDrawable)res.getDrawable(R.drawable.apple);
        mHt.put("apple", bd.getBitmap());
    }

    public Bitmap getBitmap(String file_name){
        return mHt.get(file_name);
    }

}
