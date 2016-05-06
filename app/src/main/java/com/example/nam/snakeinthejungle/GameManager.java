package com.example.nam.snakeinthejungle;

import android.content.Context;
import android.support.annotation.BoolRes;
import android.support.v4.app.NotificationCompatExtras;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Nam on 2016-04-27.
 */
public class GameManager {

    private ArrayList<Point> mBodyList;
    private ArrayList<Point> mAppleList;
    private int score;
    private Point mInnerFieldSize;
    private AudioManager mAudMgr;
    private int mDirection = EAST;
    private int mNextDirection = EAST;

    public static final int NORTH = 1;
    public static final int NEAST = 3;
    public static final int EAST = 2;
    public static final int SEAST = 6;
    public static final int SOUTH = 4;
    public static final int SWEST = 12;
    public static final int WEST = 8;
    public static final int NWEST = 9;

    private int mState;
    public static final int READY = 1;
    public static final int PAUSE = 2;
    public static final int RUNNING = 4;
    public static final int STOP = 8;

    public int getState() {
        return mState;
    }

    public GameManager(Context context) {
        mAudMgr = new AudioManager(context);

        mBodyList = new ArrayList<>();
        mAppleList = new ArrayList<>();

    }

    public boolean init() {
        mDirection = EAST;
        mNextDirection = EAST;

        if (!mBodyList.isEmpty())
            mBodyList.clear();

        if (!mAppleList.isEmpty())
            mAppleList.clear();

        score = 0;

        mBodyList.add(new Point(4, 1));
        mBodyList.add(new Point(3, 1));
        mBodyList.add(new Point(2, 1));
        mBodyList.add(new Point(1, 1));

        generateApple();

        return true;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int val) {
        score = score + val;
    }

    public boolean readyGame() {
        Log.d("SDug", "State Changed : Ready");
        mState = READY;
        this.init();
        mAudMgr.start();

        return true;
    }

    public void runGame() {
        Log.d("SDug", "State Changed : RUNNING");
        mState = RUNNING;
        mAudMgr.start();
    }

    public void pauseGame() {
        Log.d("SDug", "State Changed : PAUSE");
        mState = PAUSE;
        mAudMgr.pause();
    }

    public void resumeGame(){
        mAudMgr.start();
    }

    public void stopGame() {
        mState = STOP;
    }


    public boolean isRunning() {
        if (mState == RUNNING)
            return true;
        return false;
    }

    public boolean isPause(){
        if( mState == PAUSE)
            return true;
        return false;
    }

    public boolean isReady(){
        if( mState == READY)
            return true;
        return false;
    }

    public boolean isStop(){
        if( mState == STOP)
            return true;
        return false;
    }

    public void setInnerFieldSize(Point p) {
        mInnerFieldSize = p;
    }

    public boolean gameover() {
        Log.d("SDug", "GameOver");
        mState = STOP;
        return true;
    }

    public ArrayList<Point> getBodyList() {
        return mBodyList;
    }

    public ArrayList<Point> getAppleList() {
        return mAppleList;
    }

    public boolean setNextDirection(int Direction) {
        switch (Direction) {
            case NORTH:
                if (mDirection == SOUTH)
                    return false;
                Log.d("NamD", "방향변경 북쪽");
                break;
            case EAST:
                if (mDirection == WEST)
                    return false;
                Log.d("NamD", "방향변경 동쪽");
                break;
            case SOUTH:
                if (mDirection == NORTH)
                    return false;
                Log.d("NamD", "방향변경 남쪽");
                break;
            case WEST:
                if (mDirection == EAST)
                    return false;
                Log.d("NamD", "방향변경 서쪽");
                break;

        }
        mNextDirection = Direction;
        return true;
    }

    public int getDiection() {
        return mDirection;
    }

    public int getCompass(Point des, Point det) {
        if (des.getY() == det.getY()) {
            if (des.getX() > det.getX())
                return EAST;
            else
                return WEST;
        } else if (des.getY() < det.getY()) {
            if (des.getX() == det.getX())
                return NORTH;
            else if (des.getX() > des.getX())
                return NEAST;
            else
                return NWEST;
        } else {
            if (des.getX() == det.getX())
                return SOUTH;
            else if (des.getX() > des.getX())
                return SEAST;
            else
                return SWEST;
        }

    }

    public boolean updateSnake() {
        boolean eat_apple;

        String d = "SBodylist:";

        for (int i = 0; i < mBodyList.size(); ++i) {
            mBodyList.get(i);
            d = d + "(" + mBodyList.get(i).getX() + "," + mBodyList.get(i).getY() + ")";
        }
        Log.d("NamD", d);

        Point curPoint;
        switch (mNextDirection) {
            case NORTH:
                curPoint = new Point(mBodyList.get(0));
                if (curPoint.getY() > 0) {
                    curPoint.setY((curPoint.getY() - 1));
                    mBodyList.add(0, curPoint);
                } else {
                    this.gameover();
                    return false;
                }

                break;
            case EAST:
                curPoint = new Point(mBodyList.get(0));
                if (curPoint.getX() < mInnerFieldSize.getX()) {
                    curPoint.setX((curPoint.getX() + 1));
                    mBodyList.add(0, curPoint);
                } else {
                    this.gameover();
                    return false;
                }
                break;
            case SOUTH:
                curPoint = new Point(mBodyList.get(0));
                if (curPoint.getY() < mInnerFieldSize.getY()) {
                    curPoint.setY((curPoint.getY() + 1));
                    mBodyList.add(0, curPoint);
                } else {
                    this.gameover();
                    return false;
                }
                break;
            case WEST:
                curPoint = new Point(mBodyList.get(0));
                if (curPoint.getX() > 0) {
                    curPoint.setX((curPoint.getX() - 1));
                    mBodyList.add(0, curPoint);
                } else {
                    this.gameover();
                    return false;
                }
                break;
        }


        if (this.checkBody())
            gameover();

        eat_apple = checkApple();

        if (eat_apple) {
            score = score + 10;
            mAudMgr.eatSound();
            generateApple();
        }
        else
            mBodyList.remove(mBodyList.size() - 1);


        d = "EBodylist:";

        for (int i = 0; i < mBodyList.size(); ++i) {
            mBodyList.get(i);
            d = d + "(" + mBodyList.get(i).getX() + "," + mBodyList.get(i).getY() + ")";
        }
        Log.d("NamD", d);

        mDirection = mNextDirection;

        return true;
    }

    private boolean checkBody() {
        Point front = new Point(mBodyList.get(0));

        for (int i = 1; i < mBodyList.size(); ++i) {
            if (front.equals(mBodyList.get(i))) {
                Log.d("Nam", "몸만나서 죽음");
                return true;
            }
        }

        return false;
    }

    private boolean checkApple() {
        Point front = new Point(mBodyList.get(0));

        if (mAppleList.contains(front)) {
            for (int i = 0; i < mAppleList.size(); ++i) {
                if (front.equals(mAppleList.get(i)))
                    mAppleList.remove(i);
            }
            Log.d("Nam", "Eated apple :)");
            return true;
        }

        return false;
    }

    private int randRange(int max, int min) {
        return (int) (Math.random() * (max - min) + min);
    }

    public boolean generateApple() {
        Point Apple = new Point(randRange(0, mInnerFieldSize.getX() - 1), randRange(0, mInnerFieldSize.getY() - 1));

        int i = 0;

        while (mBodyList.contains(Apple)) {
            Apple.setX(randRange(0, mInnerFieldSize.getX() - 1));
            Apple.setY(randRange(0, mInnerFieldSize.getY() - 1));
            if (++i > 1000)
                return false;
        }

        mAppleList.add(Apple);

        Log.d("SDub", "generated Apple");

        return true;
    }
}
