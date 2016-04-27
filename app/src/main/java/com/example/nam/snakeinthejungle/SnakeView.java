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
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;
import android.view.View;

/**
 * Created by Nam on 2016-04-27.
 */
public class SnakeView extends View {
    GameData mGameData;
    BitMapContainer mBitMapContainer;

    int headx;
    int heady;
    int tick = 0;
    int last_moved_tick = 0;

    private int mDirection = EAST;
    private int mNextDirection = EAST;
    private static final int NORTH = 1;
    private static final int SOUTH = 2;
    private static final int EAST = 3;
    private static final int WEST = 4;

    public SnakeView(Context context){
        super(context);

        headx = 0;
        heady = 0;

        mGameData = new GameData();
        mGameData.init();

        mBitMapContainer = new BitMapContainer(context);

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

    private Rect setPos(int x, int y) {
        int min = Math.min(this.getWidth(),this.getHeight());
        int bgWid = mBitMapContainer.getBitmap("background").getWidth();
        double v1 = (double)min/(double)bgWid;
        double v2 = (double)bgWid/(double)500;

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

        /*배경 드로우*/
        canvas.drawBitmap(mBitMapContainer.getBitmap("background"), null, new Rect(0,0,min,min*420/500), null);

//        mGameData.getFieldMat();

        int i,j;

        for(i=0;i< 20 ;i++){
            for(j=0;j<20;j++){
                switch (mGameData.getFieldData(i,j)){
                    case GameData.HEAD:
                        switch (mDirection){
                            case NORTH:
                                canvas.drawBitmap(
                                        mBitMapContainer.getBitmap("head1")
                                        ,null, setPos(i,j),null);
                                break;
                            case SOUTH:
                                canvas.drawBitmap(
                                        mBitMapContainer.getBitmap("head2")
                                        ,null, setPos(i,j),null);
                                break;
                            case EAST:
                                canvas.drawBitmap(
                                        mBitMapContainer.getBitmap("head3")
                                        ,null, setPos(i,j),null);
                                break;
                            case WEST:
                                canvas.drawBitmap(
                                        mBitMapContainer.getBitmap("head4")
                                        ,null, setPos(i,j),null);
//                                break;
                        }
                        break;

                    case GameData.EAST:
                    case GameData.WEST:
                        canvas.drawBitmap(mBitMapContainer.getBitmap("body1"),null, setPos(i,j),null);
                        break;

                    case GameData.SOUTH:
                    case GameData.NORTH:
                        canvas.drawBitmap(mBitMapContainer.getBitmap("body2"),null, setPos(i,j),null);
                        break;

                    case GameData.NEAST:
                        canvas.drawBitmap(mBitMapContainer.getBitmap("c_body1"),null, setPos(i,j),null);
                        break;
                    case GameData.SEAST:
                        canvas.drawBitmap(mBitMapContainer.getBitmap("c_body2"),null, setPos(i,j),null);
                        break;
                    case GameData.SWEST:
                        canvas.drawBitmap(mBitMapContainer.getBitmap("c_body3"),null, setPos(i,j),null);
                        break;
                    case GameData.NWEST:
                        canvas.drawBitmap(mBitMapContainer.getBitmap("c_body4"),null, setPos(i,j),null);
                        break;

                    case GameData.NTAIL:
                        canvas.drawBitmap(mBitMapContainer.getBitmap("tail1"),null,setPos(i,j),null);
                        break;
                    case GameData.STAIL:
                        canvas.drawBitmap(mBitMapContainer.getBitmap("tail2"),null,setPos(i,j),null);
                        break;
                    case GameData.ETAIL:
                        canvas.drawBitmap(mBitMapContainer.getBitmap("tail3"),null,setPos(i,j),null);
                        break;
                    case GameData.WTAIL:
                        canvas.drawBitmap(mBitMapContainer.getBitmap("tail4"),null,setPos(i,j),null);
                        break;
                    default:
                        canvas.drawBitmap(mBitMapContainer.getBitmap("empty"),null, setPos(i,j),null);
                        break;

                }
            }
        }
//        switch (mDirection){
//            case 1:
//                canvas.drawBitmap(mBitHead1,null, setPos(headx,heady),null);
//                break;
//            case 2:
//                canvas.drawBitmap(mBitHead2,null, setPos(headx,heady),null);
//                break;
//            case 3:
//                canvas.drawBitmap(mBitHead3,null, setPos(headx,heady),null);
//                break;
//            case 4:
//                canvas.drawBitmap(mBitHead4,null, setPos(headx,heady),null);
//                break;
//        }
    }

}
