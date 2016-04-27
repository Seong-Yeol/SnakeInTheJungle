package com.example.nam.snakeinthejungle;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

/**
 * Created by Nam on 2016-04-27.
 */
public class SnakeView extends View {
    private Bitmap background;
    private Bitmap mBitHead1;
    private Bitmap mBitHead2;
    private Bitmap mBitHead3;
    private Bitmap mBitHead4;

    int headx;
    int heady;
    int tick = 0;
    int last_moved_tick = 0;


    private int mDirection = SOUTH;
    private int mNextDirection = SOUTH;
    private static final int NORTH = 1;
    private static final int SOUTH = 2;
    private static final int EAST = 3;
    private static final int WEST = 4;

    public SnakeView(Context context){
        super(context);
        Resources res = getResources();
        BitmapDrawable bd = (BitmapDrawable)res.getDrawable(R.drawable.background);
        background = bd.getBitmap();
        bd = (BitmapDrawable)res.getDrawable(R.drawable.head1);
        mBitHead1 = bd.getBitmap();
        bd = (BitmapDrawable)res.getDrawable(R.drawable.head2);
        mBitHead2 = bd.getBitmap();
        bd = (BitmapDrawable)res.getDrawable(R.drawable.head3);
        mBitHead3 = bd.getBitmap();
        bd = (BitmapDrawable)res.getDrawable(R.drawable.head4);
        mBitHead4 = bd.getBitmap();

        headx = 0;
        heady = 0;

        mHandler.sendEmptyMessage(0);

    }

    private void main() {
        int speed = 10;

        if(last_moved_tick + speed < tick) {
            last_moved_tick = tick;
            Log.d("Fnt","NextD :"+mNextDirection);
            switch (mNextDirection){

                case NORTH:
                    if( heady > 0 )
                        heady--;
                    break;
                case SOUTH:
                    if( heady < 19)
                        heady++;
                    break;
                case WEST:
                    if( headx > 0 )
                        headx--;
                    break;
                case EAST:
                    if( headx < 19 )
                        headx++;
                    break;
            }
            mDirection = mNextDirection;
            invalidate();
        }



    }

    public void Up(){
        mNextDirection = NORTH;
        Log.d("Fnt","ChangeD :" + mNextDirection);
        return;
    }

    public void Down(){
        mNextDirection = SOUTH;
        Log.d("Fnt","ChangeD :" + mNextDirection);
        return;
    }

    public void Left(){
        mNextDirection = WEST;
        Log.d("Fnt","ChangeD :" + mNextDirection);
        return;
    }

    public void Right() {
        mNextDirection = EAST;
        Log.d("Fnt","ChangeD :" + mNextDirection);
        return;
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            tick++;
            mHandler.sendEmptyMessageDelayed(0,100);
            main();
        }
    };

    private Rect HeadPos(int x, int y) {
        int min = Math.min(this.getWidth(),this.getHeight());
        double v1 = (double)min/(double)background.getWidth();
        double v2 = (double)background.getWidth()/(double)500;

        int left = (int)(x*16*v1*v2 +  (50*v1*v2));
        int top = (int)(y*16*v1*v2 +  (50*v1*v2));
        int right = left + (int)(16*v1*v2);
        int bottom = top + (int)(16*v1*v2);

        return new Rect(left, top, right, bottom);
    }

    public void onDraw(Canvas canvas){
        canvas.drawColor(Color.WHITE);

        int min = Math.min(this.getWidth(),this.getHeight());

//        Bitmap mask = Bitmap.createBitmap(background.getWidth(),background.getHeight(),Bitmap.Config.ARGB_8888);


        canvas.drawBitmap(background, null, new Rect(0,0,min,min*420/500), null);
        switch (mDirection){
            case 1:
                canvas.drawBitmap(mBitHead1,null, HeadPos(headx,heady),null);
                break;
            case 2:
                canvas.drawBitmap(mBitHead2,null, HeadPos(headx,heady),null);
                break;
            case 3:
                canvas.drawBitmap(mBitHead3,null, HeadPos(headx,heady),null);
                break;
            case 4:
                canvas.drawBitmap(mBitHead4,null, HeadPos(headx,heady),null);
                break;
        }


        double v = (double)min/(double)background.getWidth(); //

    }
}
