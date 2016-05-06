package com.example.nam.snakeinthejungle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nam on 2016-04-27.
 * 게임을 진행하는 뷰
 */

public class SnakeView extends View {
    private GameManager mGameManager;
    private BitMapContainer mBitC;
    private TextView mScoreText;

    private int mTick = 0;
    private int mLastMovedTick = 0;
    private int mLastGenApple = 0;
    private int mSnakeSpeed = 20;
    private int mAppleGenIntval = 500;

    Rect mInnerRect;

    public SnakeView(Context context) {
        super(context);

        mGameManager = new GameManager(context);
        mBitC = new BitMapContainer(context);

        mHandler.sendEmptyMessage(0);

    }

    public void setScoreTextView(TextView tv) {
        mScoreText = tv;
    }

    public void init() {
        this.ready();
    }

    public void ready() {
        mGameManager.readyGame();
        mTick = 0;
        mLastMovedTick = 0;
        mLastGenApple = 0;

        invalidate();
    }

    public void stop() {
        invalidate();
    }

    public void runGame() {
        mGameManager.runGame();
    }

    public void resume(){
        mGameManager.resumeGame();
    }

    public void pause() {
        if( mGameManager.isPause() ){
        }
        else {
            mGameManager.pauseGame();
        }
        invalidate();
    }

    private void main() {

        if (mGameManager.isRunning()) {
            if (mLastGenApple + mAppleGenIntval < mTick) {
//                mGameManager.generateApple();
                mLastGenApple = mTick;
                invalidate();
            }
            if (mLastMovedTick + mSnakeSpeed < mTick) {
                mLastMovedTick = mTick;
                mGameManager.updateSnake();
                invalidate();
            }
        }


    }

    public void Up() {
        mGameManager.setNextDirection(GameManager.NORTH);
    }

    public void Down() {
        mGameManager.setNextDirection(GameManager.SOUTH);
    }

    public void Left() {
        mGameManager.setNextDirection(GameManager.WEST);
    }

    public void Right() {
        mGameManager.setNextDirection(GameManager.EAST);
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (mGameManager.isRunning())
                mTick++;
            mHandler.sendEmptyMessageDelayed(0, 10);
            main();
        }
    };

    public Rect innerPos(Point point) {
        Rect innerRect = new Rect();
        int tail_chip_size = (int) getResources().getDimension(R.dimen.tail_chip_width);

        innerRect.set(mInnerRect.left + tail_chip_size * point.getX(), mInnerRect.top + tail_chip_size * point.getY()
                , mInnerRect.left + tail_chip_size * (point.getX() + 1), mInnerRect.top + tail_chip_size * (point.getY() + 1));

        return innerRect;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGameManager.getState() == GameManager.READY) {
            runGame();
        }
        if (mGameManager.getState() == GameManager.PAUSE) {
            runGame();
        }
        if (mGameManager.isStop() ){
            ready();
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width_pixels = MeasureSpec.getSize(widthMeasureSpec);
        int height_pixels = MeasureSpec.getSize(heightMeasureSpec);

        int gap = (int) getResources().getDimension(R.dimen.background_rect_gap);
        Bitmap background = Bitmap.createBitmap(width_pixels, height_pixels, Bitmap.Config.ARGB_8888);
        Canvas background_canvas = new Canvas(background);

        Drawable bgshape1 = getResources().getDrawable(R.drawable.bgshape1);
        Drawable bgshape2 = getResources().getDrawable(R.drawable.bgshape2);
        bgshape1.setBounds(0, 0, width_pixels, height_pixels);
        bgshape2.setBounds(gap, gap, width_pixels - gap, height_pixels - gap);
        bgshape1.draw(background_canvas);
        bgshape2.draw(background_canvas);

        mBitC.putBitmap("background", background);

        int tbheight = (int) getResources().getDimension(R.dimen.top_block_height);
        int vbwidth = (int) getResources().getDimension(R.dimen.vertical_block_chip_width);
        /*tcwh is 'Tail Chip's Width and Height'*/
        int tcwh = (int) getResources().getDimension(R.dimen.tail_chip_width);

        int inner_width = width_pixels - gap;
        int inner_horizon_blocks = inner_width / tcwh;
        int inner_padding_width = (inner_width % tcwh) / 2;

        if (inner_padding_width < tcwh / 4) {
            inner_padding_width = tcwh / 2;
            inner_horizon_blocks--;
            inner_width = inner_horizon_blocks * tcwh;
        }

        Log.d("Nam", "InnerWidth=" + inner_width + " InnerHorizonBlocks=" + inner_horizon_blocks + " InnerPaddingWid=" + inner_padding_width);

        int inner_height = height_pixels - gap;
        int inner_vertical_blocks = inner_height / tcwh;
        int inner_padding_height = (inner_height % tcwh) / 2;

        if (inner_padding_height < tcwh / 4) {
            inner_padding_height = tcwh / 2;
            inner_vertical_blocks--;
            inner_height = inner_vertical_blocks * tcwh;
        }

        Log.d("Nam", "InnerHeight=" + inner_height + " InnerVerticalBlocks=" + inner_vertical_blocks + " InnerPaddingHit=" + inner_padding_height);

        mInnerRect = new Rect( inner_padding_width, inner_padding_height
                ,  inner_padding_width + tcwh * (inner_horizon_blocks), inner_padding_height + tcwh * (inner_vertical_blocks));

        Log.d("Nam", "InnerRect=" + mInnerRect);

        mGameManager.setInnerFieldSize(new Point(inner_horizon_blocks - 1, inner_vertical_blocks - 1));

        this.init();
    }

    public void onDraw(Canvas canvas) {

        /*배경 드로우*/
        canvas.drawBitmap(mBitC.getBitmap("background"), 0, 0, null);

        int i, j;

        /*스네이크 드로우*/
        ArrayList<Point> bodyList = mGameManager.getBodyList();
        ArrayList<Point> appleList = mGameManager.getAppleList();

        /*머리부분 드로우*/
        switch (mGameManager.getDiection()) {
            case GameManager.NORTH:
                canvas.drawBitmap(
                        mBitC.getBitmap("head1")
                        , null, innerPos(bodyList.get(0)), null);
                break;
            case GameManager.SOUTH:
                canvas.drawBitmap(
                        mBitC.getBitmap("head2")
                        , null, innerPos(bodyList.get(0)), null);
                break;
            case GameManager.EAST:
                canvas.drawBitmap(
                        mBitC.getBitmap("head3")
                        , null, innerPos(bodyList.get(0)), null);
                break;
            case GameManager.WEST:
                canvas.drawBitmap(
                        mBitC.getBitmap("head4")
                        , null, innerPos(bodyList.get(0)), null);
                break;
        }

        /*몸통부분드로우*/
        int compass = 0, frontcompass, backcompass;
        for (i = 1; i < bodyList.size() - 1; i++) {

            frontcompass = mGameManager.getCompass(bodyList.get(i - 1), bodyList.get(i));
            backcompass = mGameManager.getCompass(bodyList.get(i), bodyList.get(i + 1));

            if (frontcompass == backcompass)
                compass = frontcompass;
            else {
                switch (backcompass) {
                    case GameManager.NORTH:
                        switch (frontcompass) {
                            case GameManager.EAST:
                                compass = GameManager.SWEST;
                                break;
                            case GameManager.WEST:
                                compass = GameManager.SEAST;
                                break;
                        }
                        break;
                    case GameManager.SOUTH:
                        switch (frontcompass) {
                            case GameManager.EAST:
                                compass = GameManager.NWEST;
                                break;
                            case GameManager.WEST:
                                compass = GameManager.NEAST;
                                break;
                        }
                        break;
                    case GameManager.EAST:
                        switch (frontcompass) {
                            case GameManager.NORTH:
                                compass = GameManager.NEAST;
                                break;
                            case GameManager.SOUTH:
                                compass = GameManager.SEAST;
                                break;
                        }
                        break;
                    case GameManager.WEST:
                        switch (frontcompass) {
                            case GameManager.NORTH:
                                compass = GameManager.NWEST;
                                break;
                            case GameManager.SOUTH:
                                compass = GameManager.SWEST;
                                break;
                        }
                        break;
                }
            }


            switch (compass) {
                case GameManager.NORTH:
                case GameManager.SOUTH:
                    canvas.drawBitmap(
                            mBitC.getBitmap("body2")
                            , null, innerPos(bodyList.get(i)), null);
                    break;
                case GameManager.EAST:
                case GameManager.WEST:
                    canvas.drawBitmap(
                            mBitC.getBitmap("body1")
                            , null, innerPos(bodyList.get(i)), null);

                    break;

                case GameManager.NEAST:
                    canvas.drawBitmap(
                            mBitC.getBitmap("c_body1")
                            , null, innerPos(bodyList.get(i)), null);
                    break;
                case GameManager.SEAST:
                    canvas.drawBitmap(
                            mBitC.getBitmap("c_body2")
                            , null, innerPos(bodyList.get(i)), null);
                    break;
                case GameManager.SWEST:
                    canvas.drawBitmap(
                            mBitC.getBitmap("c_body3")
                            , null, innerPos(bodyList.get(i)), null);
                    break;
                case GameManager.NWEST:
                    canvas.drawBitmap(
                            mBitC.getBitmap("c_body4")
                            , null, innerPos(bodyList.get(i)), null);
                    break;
            }
        }

        /*꼬리부분 드로우*/
        compass = mGameManager.getCompass(bodyList.get(i - 1), bodyList.get(i));
        switch (compass) {
            case GameManager.NORTH:
                canvas.drawBitmap(
                        mBitC.getBitmap("tail1")
                        , null, innerPos(bodyList.get(i)), null);
                break;
            case GameManager.SOUTH:
                canvas.drawBitmap(
                        mBitC.getBitmap("tail2")
                        , null, innerPos(bodyList.get(i)), null);
                break;
            case GameManager.EAST:
                canvas.drawBitmap(
                        mBitC.getBitmap("tail3")
                        , null, innerPos(bodyList.get(i)), null);
                break;
            case GameManager.WEST:
                canvas.drawBitmap(
                        mBitC.getBitmap("tail4")
                        , null, innerPos(bodyList.get(i)), null);
                break;
        }


        for (i = 0; i < appleList.size(); ++i) {
            canvas.drawBitmap(
                    mBitC.getBitmap("apple")
                    , null, innerPos(appleList.get(i)), null);

        }

        if (mGameManager.isReady()) {
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setAntiAlias(true);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize((int) getResources().getDimension(R.dimen.game_message_font_size));
            canvas.drawText("터치하여 시작하기", this.getWidth() / 2, this.getHeight() / 2, paint);
        }

        if (mGameManager.isPause()) {
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setAntiAlias(true);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize((int) getResources().getDimension(R.dimen.game_message_font_size));
            canvas.drawText("(일시정지)터치하여 시작하기", this.getWidth() / 2, this.getHeight() / 2, paint);
        }

        if(mGameManager.isStop()){
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setAntiAlias(true);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize((int) getResources().getDimension(R.dimen.game_message_font_size));
            canvas.drawText("최종 스코어 : " + mGameManager.getScore(), this.getWidth() / 2,(this.getHeight() - (int)getResources().getDimension(R.dimen.game_message_font_size))/ 2, paint);
            canvas.drawText("터치하여 리셋하기", this.getWidth() / 2,(this.getHeight() + (int)getResources().getDimension(R.dimen.game_message_font_size))/ 2, paint);
        }

        mScoreText.setText("" + mGameManager.getScore());

    }
}
