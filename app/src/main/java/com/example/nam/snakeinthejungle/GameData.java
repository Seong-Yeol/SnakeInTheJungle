package com.example.nam.snakeinthejungle;

import android.support.v4.app.NotificationCompatExtras;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Nam on 2016-04-27.
 */
public class GameData {

    private ArrayList<Point> mBodyList;
    private ArrayList<Point> mAppleList;

    int [][]mFieldMat = new int[20][20];

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

    public static final int NTAIL = 10;
    public static final int STAIL = 11;
    public static final int ETAIL = 12;
    public static final int WTAIL = 13;

    int mHeadX;
    int mHeadY;


    public GameData(){

        mBodyList = new ArrayList<>();
        mAppleList = new ArrayList<>();
    }

    public boolean init() {
        int i,j;

//        for(i=0; i < 20; i++){
//            for(j=0; j < 20; j++){
//                mFieldMat[i][j] = EMPTY;
//            }
//        }
        mDirection = EAST;
        mNextDirection = EAST;

        mBodyList.add(new Point(4,1));
        mBodyList.add(new Point(3,1));
        mBodyList.add(new Point(2,1));
        mBodyList.add(new Point(1,1));
/*        mFieldMat[1][1] = ETAIL;
        mFieldMat[2][1] = EAST;
        mFieldMat[3][1] = EAST;
        mFieldMat[4][1] = HEAD;*/

        mHeadX = 4;
        mHeadX = 1;

        return true;
    }

    public boolean gameover(){
        Log.d("SDug","GameOver");
        Log.d("Nam","gameover called");

        if(!mBodyList.isEmpty())
            mBodyList.clear();

        if(!mAppleList.isEmpty())
            mAppleList.clear();

        this.init();

        Log.d("Nam","gameover returned");

        return true;
    }

    public ArrayList<Point> getBodyList(){
        return mBodyList;
    }

    public ArrayList<Point> getAppleList(){return mAppleList;}

    public boolean setNextDirection(int Direction){
        switch(Direction){
            case NORTH:
                if(mDirection == SOUTH)
                    return false;
                Log.d("NamD","방향변경 북쪽");
                break;
            case EAST:
                if(mDirection == WEST)
                    return false;
                Log.d("NamD","방향변경 동쪽");
                break;
            case SOUTH:
                if(mDirection == NORTH)
                    return false;
                Log.d("NamD","방향변경 남쪽");
                break;
            case WEST:
                if(mDirection == EAST)
                    return false;
                Log.d("NamD","방향변경 서쪽");
                break;

        }
        mNextDirection = Direction;
        return true;
    }

    public int getDiection(){return mDirection;}

    public int getCompass(Point des, Point det){
        if(des.getY() == det.getY()){
            if(des.getX() > det.getX())
                return EAST;
            else
                return WEST;
        }
        else if(des.getY() < det.getY() ){
            if(des.getX() == det.getX())
                return NORTH;
            else if (des.getX() > des.getX())
                return NEAST;
            else
                return NWEST;
        }else{
            if(des.getX() == det.getX())
                return SOUTH;
            else if (des.getX() > des.getX())
                return SEAST;
            else
                return SWEST;
        }

    }



    public int getCompass(Point des, Point cur, Point det){
        if(des.getY() == det.getY()){
            if(des.getX() > det.getX())
                return EAST;
            else
                return WEST;
        }
        else if(des.getY() < det.getY() ){
            if(des.getX() == det.getX())
                return NORTH;
            else if (des.getX() > des.getX())
                return NEAST;
            else
                return NWEST;
        }else{
            if(des.getX() == det.getX())
                return SOUTH;
            else if (des.getX() > des.getX())
                return SEAST;
            else
                return SWEST;
        }

    }

    public boolean updateSnake(){
        boolean eat_apple;

        String d = "SBodylist:";

        for(int i =0; i<mBodyList.size();++i){
            mBodyList.get(i);
            d = d + "(" + mBodyList.get(i).getX() + "," + mBodyList.get(i).getY() + ")";
        }
        Log.d("NamD",d);



        Point curPoint;
        switch(mNextDirection){
            case NORTH:
                curPoint = new Point(mBodyList.get(0));
                if(curPoint.getY() > 0) {
                    curPoint.setY((curPoint.getY() - 1));
                    mBodyList.add(0, curPoint);
                }else{
                    this.gameover();
                    return false;
                }

                break;
            case EAST:
                curPoint = new Point(mBodyList.get(0));
                if(curPoint.getX() < 20){
                    curPoint.setX((curPoint.getX()+1));
                    mBodyList.add(0,curPoint);
                }else{
                    this.gameover();
                    return false;
                }
                break;
            case SOUTH:
                curPoint = new Point(mBodyList.get(0));
                if(curPoint.getY()<20){
                    curPoint.setY((curPoint.getY()+1));
                    mBodyList.add(0,curPoint);
                }else{
                    this.gameover();
                    return false;
                }
                break;
            case WEST:
                curPoint = new Point(mBodyList.get(0));
                if(curPoint.getX()>0){
                    curPoint.setX((curPoint.getX()-1));
                    mBodyList.add(0,curPoint);
                }else{
                    this.gameover();
                    return false;
                }
                break;
        }


        if( this.checkBody() )
            gameover();

        eat_apple = checkApple();


        if(!eat_apple)
            mBodyList.remove(mBodyList.size()-1);


        d = "EBodylist:";

        for(int i =0; i<mBodyList.size();++i){
            mBodyList.get(i);
            d = d + "(" + mBodyList.get(i).getX() + "," + mBodyList.get(i).getY() + ")";
        }
        Log.d("NamD",d);

        mDirection = mNextDirection;

        return true;
    }

    private boolean checkBody(){
        Point front = new Point(mBodyList.get(0));

//        switch(mNextDirection) {
//            case NORTH:
//                front.setY(front.getY() - 1);
//                break;
//            case EAST:
//                front.setY(front.getX() + 1);
//                break;
//            case SOUTH:
//                front.setX(front.getY() + 1 );
//                break;
//            case WEST:
//                front.setX(front.getX() - 1 );
//                break;
//        }

        for( int i = 1 ; i < mBodyList.size() ; ++i ){
            if(front.equals(mBodyList.get(i))){
                Log.d("Nam","몸만나서 죽음");
                return true;
            }
        }

        return false;
    }

    private boolean checkApple(){
        Point front = new Point(mBodyList.get(0));

//        switch(mNextDirection) {
//            case NORTH:
//                front.setY(front.getY() - 1);
//                break;
//            case EAST:
//                front.setY(front.getX() + 1);
//                break;
//            case SOUTH:
//                front.setX(front.getY() + 1 );
//                break;
//            case WEST:
//                front.setX(front.getX() - 1 );
//                break;
//        }

        if(mAppleList.contains(front)){
            for( int i = 0; i< mAppleList.size() ; ++i){
                if(front.equals(mAppleList.get(i)))
                    mAppleList.remove(i);
            }
            Log.d("Nam", "Eated apple :)");
            return true;
        }

        return false;
    }

    private int randRange(int max, int min){
        return (int)(Math.random()*(max - min) + min);
    }

    public boolean generateApple(){
        Point Apple = new Point(randRange(0,19),randRange(0,19));

        int i=0;
        while (mBodyList.contains(Apple) ){
            Apple.setX(randRange(0,19));
            Apple.setY(randRange(0,19));
            if(++i > 1000)
                return false;
        }

        mAppleList.add(Apple);

        Log.d("SDub","generated Apple");


        return true;
    }

    final int [][] getFieldMat() {
        return mFieldMat;
    }

    public int getFieldData(int x, int y){
        return mFieldMat[x][y];
    }


}
