package com.example.nam.snakeinthejungle;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.NfcEvent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;

import java.util.ArrayList;

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
    int mLastGenApple = 0;

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
        int speed = 50;
        int makeGenAppleTick = 300;

        if(mLastGenApple + makeGenAppleTick < tick) {
            mGameData.generateApple();
            mLastGenApple = tick;
        }
        if(last_moved_tick + speed < tick) {
            last_moved_tick = tick;
            mGameData.updateSnake();
        }


        invalidate();
    }




    public void Up(){
        mGameData.setNextDirection(GameData.NORTH);
        return;
    }

    public void Down(){
        mGameData.setNextDirection(GameData.SOUTH);
        return;
    }

    public void Left(){
        mGameData.setNextDirection(GameData.WEST);
        return;
    }

    public void Right() {
        mGameData.setNextDirection(GameData.EAST);
        return;
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            tick++;
            mHandler.sendEmptyMessageDelayed(0,10);
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

    private Rect setPos(Point point) {
        int min = Math.min(this.getWidth(),this.getHeight());
        int bgWid = mBitMapContainer.getBitmap("background").getWidth();
        double v1 = (double)min/(double)bgWid;
        double v2 = (double)bgWid/(double)500;

        int left = (int)(point.getX()*16*v1*v2 +  (50*v1*v2));
        int top = (int)(point.getY()*16*v1*v2 +  (50*v1*v2));
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

        /*스네이크 드로우*/
        ArrayList<Point> bodyList = mGameData.getBodyList();
        ArrayList<Point> appleList = mGameData.getAppleList();

        /*머리부분 드로우*/
        switch(mGameData.getDiection()){
            case GameData.NORTH:
                canvas.drawBitmap(
                        mBitMapContainer.getBitmap("head1")
                        ,null, setPos(bodyList.get(0)),null);
                break;
            case GameData.SOUTH:
                canvas.drawBitmap(
                        mBitMapContainer.getBitmap("head2")
                        ,null, setPos(bodyList.get(0)),null);
                break;
            case GameData.EAST:
                canvas.drawBitmap(
                        mBitMapContainer.getBitmap("head3")
                        ,null, setPos(bodyList.get(0)),null);
                break;
            case GameData.WEST:
                canvas.drawBitmap(
                        mBitMapContainer.getBitmap("head4")
                        ,null, setPos(bodyList.get(0)),null);
                break;
        }

        /*몸통부분드로우*/
        int compass = 0 , frontcompass, backcompass;
        for(i=1; i<bodyList.size()-1; i++){

            frontcompass = mGameData.getCompass(bodyList.get(i-1),bodyList.get(i));
            backcompass = mGameData.getCompass(bodyList.get(i),bodyList.get(i+1));

            if( frontcompass == backcompass )
                compass = frontcompass;
            else{
                switch (backcompass) {
                    case GameData.NORTH:
                        switch (frontcompass) {
                            case GameData.EAST:
                                compass = GameData.SWEST;break;
                            case GameData.WEST:
                                compass = GameData.SEAST;break;
                        } break;
                    case GameData.SOUTH:
                        switch (frontcompass) {
                            case GameData.EAST:
                                compass = GameData.NWEST;break;
                            case GameData.WEST:
                                compass = GameData.NEAST;break;
                        }break;
                    case GameData.EAST:
                        switch (frontcompass) {
                            case GameData.NORTH:
                                compass = GameData.NEAST;
                                break;
                            case GameData.SOUTH:
                                compass = GameData.SEAST;
                                break;
                        }break;
                    case GameData.WEST:
                        switch (frontcompass) {
                            case GameData.NORTH:
                                compass = GameData.NWEST;
                                break;
                            case GameData.SOUTH:
                                compass = GameData.SWEST;
                                break;
                        }break;
                }
            }




            switch (compass){
                case GameData.NORTH:
                case GameData.SOUTH:
                    canvas.drawBitmap(
                            mBitMapContainer.getBitmap("body2")
                            , null, setPos(bodyList.get(i)), null);
                    break;
                case GameData.EAST:
                case GameData.WEST:
                    canvas.drawBitmap(
                            mBitMapContainer.getBitmap("body1")
                            , null, setPos(bodyList.get(i)), null);

                    break;

                case GameData.NEAST:
                    canvas.drawBitmap(
                            mBitMapContainer.getBitmap("c_body1")
                            , null, setPos(bodyList.get(i)), null);
                    break;
                case GameData.SEAST:
                    canvas.drawBitmap(
                            mBitMapContainer.getBitmap("c_body2")
                            , null, setPos(bodyList.get(i)), null);
                    break;
                case GameData.SWEST:
                    canvas.drawBitmap(
                            mBitMapContainer.getBitmap("c_body3")
                            , null, setPos(bodyList.get(i)), null);
                    break;
                case GameData.NWEST:
                    canvas.drawBitmap(
                            mBitMapContainer.getBitmap("c_body4")
                            , null, setPos(bodyList.get(i)), null);
                    break;
            }
        }

        /*꼬리부분 드로우*/
        compass = mGameData.getCompass(bodyList.get(i-1),bodyList.get(i));
        switch(compass){
            case GameData.NORTH:
                canvas.drawBitmap(
                        mBitMapContainer.getBitmap("tail1")
                        ,null, setPos(bodyList.get(i)),null);
                break;
            case GameData.SOUTH:
                canvas.drawBitmap(
                        mBitMapContainer.getBitmap("tail2")
                        ,null, setPos(bodyList.get(i)),null);
                break;
            case GameData.EAST:
                canvas.drawBitmap(
                        mBitMapContainer.getBitmap("tail3")
                        ,null, setPos(bodyList.get(i)),null);
                break;
            case GameData.WEST:
                canvas.drawBitmap(
                        mBitMapContainer.getBitmap("tail4")
                        ,null, setPos(bodyList.get(i)),null);
                break;
        }

        for(i=0; i < appleList.size(); ++i){
            canvas.drawBitmap(
                    mBitMapContainer.getBitmap("apple")
                    ,null, setPos(appleList.get(i)),null);

        }
    }

}
