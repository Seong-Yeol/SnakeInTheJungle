package com.example.nam.snakeinthejungle;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;

import com.example.nam.snakeinthejungle.R;

import java.util.Hashtable;

/**
 * Created by Nam on 2016-04-28.
 * 사용되는 BitMap 자료들을 담고있는 콘테이너
 */
public class BitMapContainer extends View {

    private Hashtable<String,Bitmap> mHt;
    Rect mInnerRect;

    public BitMapContainer(Context context){
        super(context);
        mHt = new Hashtable<>();
        Resources res = getResources();

        Bitmap bit;

        Bitmap corner_block1;
        Bitmap corner_block2;
        Bitmap corner_block3;
        Bitmap corner_block4;
        Bitmap top_block;
        Bitmap bottom_block;
        Bitmap left_block;
        Bitmap right_block;
        Bitmap lion;
        Bitmap head1;
        Bitmap head2;
        Bitmap head3;
        Bitmap head4;
        Bitmap body1;
        Bitmap body2;
        Bitmap c_body1;
        Bitmap c_body2;
        Bitmap c_body3;
        Bitmap c_body4;
        Bitmap tail1;
        Bitmap tail2;
        Bitmap tail3;
        Bitmap tail4;
        Bitmap apple;


        Matrix mat = new Matrix();


        bit = BitmapFactory.decodeResource(getResources(),R.mipmap.corner_block_chip);
        corner_block1 = Bitmap.createBitmap(bit,
                0, 0,
                (int)getResources().getDimension(R.dimen.corner_block_width), (int)getResources().getDimension(R.dimen.corner_block_height));
        mat.postScale(-1,1);
        corner_block2 = Bitmap.createBitmap(corner_block1,0,0,corner_block1.getWidth(),corner_block1.getHeight(),mat,true);
        mat.postScale(-1,-1);
        corner_block3 = Bitmap.createBitmap(corner_block1,0,0,corner_block1.getWidth(),corner_block1.getHeight(),mat,true);
        mat.postScale(-1,1);
        corner_block4 = Bitmap.createBitmap(corner_block1,0,0,corner_block1.getWidth(),corner_block1.getHeight(),mat,true);

        bit = BitmapFactory.decodeResource(getResources(),R.mipmap.top_block_chip);
        top_block = Bitmap.createBitmap(bit, 0, 0,
                (int)getResources().getDimension(R.dimen.top_block_width),
                (int)getResources().getDimension(R.dimen.top_block_height));
        mat.postScale(-1,1);
        bottom_block = Bitmap.createBitmap(top_block,0,0,top_block.getWidth(),top_block.getHeight(), mat, true);

        bit = BitmapFactory.decodeResource(getResources(), R.mipmap.left_block_chip);
        left_block = Bitmap.createBitmap(bit,0,0,
                (int)getResources().getDimension(R.dimen.vertical_block_chip_width),
                (int)getResources().getDimension(R.dimen.vertical_block_chip_height));
        mat.postScale(-1,1);
        right_block = Bitmap.createBitmap(left_block, 0,0,left_block.getWidth(),left_block.getHeight(),mat,true);

        bit = BitmapFactory.decodeResource(getResources(),R.mipmap.lion_chip);
        lion = Bitmap.createBitmap(bit, 0, 0,
                (int)getResources().getDimension(R.dimen.lion_chip_width),
                (int)getResources().getDimension(R.dimen.lion_chip_height));

        mHt.put("corner_block1",corner_block1);
        mHt.put("corner_block2",corner_block2);
        mHt.put("corner_block3",corner_block3);
        mHt.put("corner_block4",corner_block4);

        mHt.put("left_block",left_block);
        mHt.put("right_block",right_block);

        mHt.put("top_block", top_block);
        mHt.put("bottom_block", bottom_block);

        mHt.put("lion",lion);


        /*tcwh is 'Tail Chip's Width and Height'*/
        int tcwh = (int)getResources().getDimension(R.dimen.tail_chip_width);
        mat = new Matrix();
        bit = BitmapFactory.decodeResource(getResources(),R.mipmap.head);
        head1 = Bitmap.createBitmap(bit,0, 0, tcwh, tcwh);
        mat.postScale(1,-1);
        head2 = Bitmap.createBitmap(head1,0,0,tcwh,tcwh,mat,true);
        mat.postRotate((float)-90);
        head3 = Bitmap.createBitmap(head1,0,0,tcwh,tcwh,mat,true);
        mat.postScale(-1,1);
        head4 = Bitmap.createBitmap(head1,0,0,tcwh,tcwh,mat,true);

        bit = BitmapFactory.decodeResource(getResources(),R.mipmap.body);
        body1 = Bitmap.createBitmap(bit, 0, 0, tcwh, tcwh);
        body2 = Bitmap.createBitmap(body1,0,0,tcwh,tcwh,mat,true);

        mat = new Matrix();
        bit = BitmapFactory.decodeResource(getResources(),R.mipmap.tail);
        tail1 = Bitmap.createBitmap(bit,0, 0, tcwh, tcwh);
        mat.postScale(1,-1);
        tail2 = Bitmap.createBitmap(tail1,0,0,tcwh,tcwh,mat,true);
        mat.postRotate((float)-90);
        tail3 = Bitmap.createBitmap(tail1,0,0,tcwh,tcwh,mat,true);
        mat.postScale(-1,1);
        tail4 = Bitmap.createBitmap(tail1,0,0,tcwh,tcwh,mat,true);

        mat = new Matrix();
        bit = BitmapFactory.decodeResource(getResources(),R.mipmap.cuv_body);
        c_body1 = Bitmap.createBitmap(bit,0, 0, tcwh, tcwh);
        mat.postRotate((float)-90);
        c_body2 = Bitmap.createBitmap(c_body1,0,0,tcwh,tcwh,mat,true);
        mat.postRotate((float)-90);
        c_body3 = Bitmap.createBitmap(c_body1,0,0,tcwh,tcwh,mat,true);
        mat.postRotate((float)-90);
        c_body4 = Bitmap.createBitmap(c_body1,0,0,tcwh,tcwh,mat,true);

        mHt.put("head1", head1);
        mHt.put("head2", head2);
        mHt.put("head3", head3);
        mHt.put("head4", head4);

        mHt.put("body1", body1);
        mHt.put("body2", body2);

        mHt.put("c_body1", c_body1);
        mHt.put("c_body2", c_body2);
        mHt.put("c_body3", c_body3);
        mHt.put("c_body4", c_body4);

        mHt.put("tail1", tail1);
        mHt.put("tail2", tail2);
        mHt.put("tail3", tail3);
        mHt.put("tail4", tail4);

        bit = BitmapFactory.decodeResource(getResources(),R.mipmap.apple);
        apple = Bitmap.createBitmap(bit,0, 0, tcwh, tcwh);
        mHt.put("apple", apple);

//        int width_picxels = this.getWidth();
//        int height_picxels = this.getHeight();
//
//        int cbwidth = corner_block1.getWidth();
//        int cbheight = corner_block1.getHeight();
//        int tbwidth = top_block.getWidth();
//        int tbheight = top_block.getHeight();
//        int vbwidth = left_block.getWidth();
//        int vbheigh = right_block.getHeight();

//        Log.d("Nam","ViewWid=" + width_picxels + " ViewHit =" + height_picxels);
//        Log.d("Nam","VerticalBlockWid=" + vbwidth + " Horizon_Hit =" + tbheight);
//        Log.d("Nam","TailChipSize="+ tcwh);
//
//        int inner_width = width_picxels - vbwidth*2;
//        int inner_horizon_blocks = inner_width / tcwh;
//        int inner_padding_width = (inner_width%tcwh)/2;
//
//        if( inner_padding_width < tcwh / 4){
//            inner_padding_width = tcwh / 2;
//            inner_horizon_blocks--;
//            inner_width = inner_horizon_blocks * tcwh;
//        }
//
//        Log.d("Nam","InnerWidth="+inner_width + " InnerHorizonBlocks=" + inner_horizon_blocks + " InnerPaddingWid="+ inner_padding_width);
//
//        int inner_height = height_picxels - tbheight*2;
//        int inner_vertical_blocks = inner_height / tcwh;
//        int inner_padding_height = (inner_height%tcwh)/2;
//
//        if( inner_padding_height < tcwh / 4){
//            inner_padding_height = tcwh / 2;
//            inner_vertical_blocks--;
//            inner_height = inner_vertical_blocks * tcwh;
//        }
//
//        Log.d("Nam","InnerHeight="+inner_height + " InnerVerticalBlocks=" + inner_vertical_blocks + " InnerPaddingHit="+ inner_padding_height);
//
//        mInnerRect = new Rect(vbwidth + inner_padding_width, tbheight + inner_padding_height
//                ,vbwidth + inner_padding_width + tcwh*(inner_horizon_blocks), tbheight + inner_padding_height + tcwh*(inner_vertical_blocks));
//
//        Log.d("Nam","InnerRect=" + mInnerRect);


    }

    public Bitmap getBitmap(String file_name){
        return mHt.get(file_name);
    }

}
