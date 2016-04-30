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
    BitMapContainer mBitC;

    int headx;
    int heady;
    int tick = 0;
    int last_moved_tick = 0;
    int mLastGenApple = 0;
    Rect mInnerRect;
    boolean initedOnDraw = false;

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

        mBitC = new BitMapContainer(context);

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
        return setPos(new Point(x,y));
    }

    public Rect innerPos(Point point) {
        Rect innerRect = new Rect();
        int tail_chip_size = (int)getResources().getDimension(R.dimen.tail_chip_width);

        innerRect.set(mInnerRect.left + tail_chip_size*point.getX(), mInnerRect.top + tail_chip_size*point.getY()
                ,mInnerRect.left + tail_chip_size*(point.getX()+1), mInnerRect.top + tail_chip_size*(point.getY()+1));

        return innerRect;
    }

    private Rect setPos(Point point) {
        int min = Math.min(this.getWidth(),this.getHeight());
        int bgWid = mBitC.getBitmap("background").getWidth();
        double v1 = (double)min/(double)bgWid;
        double v2 = (double)bgWid/(double)500;



        int left = (int)((double)point.getX()*(double)16*v1*v2 +  ((double)50*v1*v2));
        int top = (int)((double)point.getY()*(double)16*v1*v2 +  ((double)50*v1*v2));
        int right = left + (int)((double)16*v1*v2);
        int bottom = top + (int)((double)16*v1*v2);

        return new Rect(left, top, right, bottom);
    }

    private void initOnDraw(){
        int width_picxels = this.getWidth();
        int height_picxels = this.getHeight();

        int tbheight = (int)getResources().getDimension(R.dimen.top_block_height);
        int vbwidth = (int)getResources().getDimension(R.dimen.vertical_block_chip_width);
        /*tcwh is 'Tail Chip's Width and Height'*/
        int tcwh = (int)getResources().getDimension(R.dimen.tail_chip_width);

        int inner_width = width_picxels - vbwidth*2;
        int inner_horizon_blocks = inner_width / tcwh;
        int inner_padding_width = (inner_width%tcwh)/2;

        if( inner_padding_width < tcwh / 4){
            inner_padding_width = tcwh / 2;
            inner_horizon_blocks--;
            inner_width = inner_horizon_blocks * tcwh;
        }

        Log.d("Nam","InnerWidth="+inner_width + " InnerHorizonBlocks=" + inner_horizon_blocks + " InnerPaddingWid="+ inner_padding_width);

        int inner_height = height_picxels - tbheight*2;
        int inner_vertical_blocks = inner_height / tcwh;
        int inner_padding_height = (inner_height%tcwh)/2;

        if( inner_padding_height < tcwh / 4){
            inner_padding_height = tcwh / 2;
            inner_vertical_blocks--;
            inner_height = inner_vertical_blocks * tcwh;
        }

        Log.d("Nam","InnerHeight="+inner_height + " InnerVerticalBlocks=" + inner_vertical_blocks + " InnerPaddingHit="+ inner_padding_height);

        mInnerRect = new Rect(vbwidth + inner_padding_width, tbheight + inner_padding_height
                ,vbwidth + inner_padding_width + tcwh*(inner_horizon_blocks), tbheight + inner_padding_height + tcwh*(inner_vertical_blocks));

        Log.d("Nam","InnerRect=" + mInnerRect);

        mGameData.setInnerFieldSize(new Point(inner_horizon_blocks,inner_vertical_blocks));
    }

    public void onDraw(Canvas canvas){
        if( !initedOnDraw ){
            initOnDraw();
            initedOnDraw = true;
        }

        /*배경 드로우*/
//        canvas.drawBitmap(mBitC.getBitmap("background"), null, new Rect(0,0,min,min*420/500), null);
        Bitmap corner_block1 = mBitC.getBitmap("corner_block1");
        Bitmap corner_block2 = mBitC.getBitmap("corner_block2");
        Bitmap corner_block3 = mBitC.getBitmap("corner_block3");
        Bitmap corner_block4 = mBitC.getBitmap("corner_block4");
        Bitmap top_block = mBitC.getBitmap("top_block");
        Bitmap bottom_block = mBitC.getBitmap("bottom_block");
        Bitmap left_block = mBitC.getBitmap("left_block");
        Bitmap right_block = mBitC.getBitmap("right_block");
        Bitmap lion = mBitC.getBitmap("lion");

        int width_picexls = this.getWidth();
        int heidth_picexls = this.getHeight();

        int cbwidth = corner_block1.getWidth();
        int cbheight = corner_block1.getHeight();
        int tbwidth = top_block.getWidth();
        int tbheight = top_block.getHeight();
        int vbwidth = left_block.getWidth();
        int vbheigh = right_block.getHeight();

        canvas.drawColor(Color.GREEN);

        for(int i=1; cbwidth+tbwidth*i < width_picexls ; ++i){
            canvas.drawBitmap(top_block, null, new Rect(cbwidth+tbwidth*(i-1),0,cbwidth+tbwidth*i,tbheight), null);
            canvas.drawBitmap(bottom_block, null, new Rect(cbwidth+tbwidth*(i-1),heidth_picexls-tbheight,cbwidth+tbwidth*i,heidth_picexls), null);
        }

        for(int i=1; cbheight+vbheigh*i < heidth_picexls ; ++i){
            canvas.drawBitmap(left_block, null, new Rect(0,cbheight+vbheigh*(i-1),vbwidth,cbheight+vbheigh*i), null);
            canvas.drawBitmap(right_block, null, new Rect(width_picexls-vbwidth,cbheight+vbheigh*(i-1),width_picexls,cbheight+vbheigh*i), null);
        }

        canvas.drawBitmap(corner_block1, null, new Rect(0,0,cbwidth,cbheight), null);
        canvas.drawBitmap(corner_block2, null, new Rect(width_picexls-cbwidth,0,width_picexls,cbheight), null);
        canvas.drawBitmap(corner_block3, null, new Rect(0,heidth_picexls-cbheight,cbwidth,heidth_picexls), null);
        canvas.drawBitmap(corner_block4, null, new Rect(width_picexls-cbwidth,heidth_picexls-cbheight,width_picexls,heidth_picexls), null);

        canvas.drawBitmap(lion, null, new Rect(width_picexls-lion.getWidth(),heidth_picexls-lion.getHeight(),width_picexls,heidth_picexls),null);


        int i,j;

        /*스네이크 드로우*/
        ArrayList<Point> bodyList = mGameData.getBodyList();
        ArrayList<Point> appleList = mGameData.getAppleList();

        /*머리부분 드로우*/
        switch(mGameData.getDiection()){
            case GameData.NORTH:
                canvas.drawBitmap(
                        mBitC.getBitmap("head1")
                        ,null, innerPos(bodyList.get(0)),null);
                break;
            case GameData.SOUTH:
                canvas.drawBitmap(
                        mBitC.getBitmap("head2")
                        ,null, innerPos(bodyList.get(0)),null);
                break;
            case GameData.EAST:
                canvas.drawBitmap(
                        mBitC.getBitmap("head3")
                        ,null, innerPos(bodyList.get(0)),null);
                break;
            case GameData.WEST:
                canvas.drawBitmap(
                        mBitC.getBitmap("head4")
                        ,null, innerPos(bodyList.get(0)),null);
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
                            mBitC.getBitmap("body2")
                            , null, innerPos(bodyList.get(i)), null);
                    break;
                case GameData.EAST:
                case GameData.WEST:
                    canvas.drawBitmap(
                            mBitC.getBitmap("body1")
                            , null, innerPos(bodyList.get(i)), null);

                    break;

                case GameData.NEAST:
                    canvas.drawBitmap(
                            mBitC.getBitmap("c_body1")
                            , null, innerPos(bodyList.get(i)), null);
                    break;
                case GameData.SEAST:
                    canvas.drawBitmap(
                            mBitC.getBitmap("c_body2")
                            , null, innerPos(bodyList.get(i)), null);
                    break;
                case GameData.SWEST:
                    canvas.drawBitmap(
                            mBitC.getBitmap("c_body3")
                            , null, innerPos(bodyList.get(i)), null);
                    break;
                case GameData.NWEST:
                    canvas.drawBitmap(
                            mBitC.getBitmap("c_body4")
                            , null, innerPos(bodyList.get(i)), null);
                    break;
            }
        }

        /*꼬리부분 드로우*/
        compass = mGameData.getCompass(bodyList.get(i-1),bodyList.get(i));
        switch(compass){
            case GameData.NORTH:
                canvas.drawBitmap(
                        mBitC.getBitmap("tail1")
                        ,null, innerPos(bodyList.get(i)),null);
                break;
            case GameData.SOUTH:
                canvas.drawBitmap(
                        mBitC.getBitmap("tail2")
                        ,null, innerPos(bodyList.get(i)),null);
                break;
            case GameData.EAST:
                canvas.drawBitmap(
                        mBitC.getBitmap("tail3")
                        ,null, innerPos(bodyList.get(i)),null);
                break;
            case GameData.WEST:
                canvas.drawBitmap(
                        mBitC.getBitmap("tail4")
                        ,null, innerPos(bodyList.get(i)),null);
                break;
        }

        for(i=0; i < appleList.size(); ++i){
            canvas.drawBitmap(
                    mBitC.getBitmap("apple")
                    ,null, innerPos(appleList.get(i)),null);

        }
    }

}
