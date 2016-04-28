package com.example.nam.snakeinthejungle;

import android.view.GestureDetector;

/**
 * Created by Nam on 2016-04-28.
 */
public class Point {
    private int mX;
    private int mY;

    public Point(){mX=mY=0;}
    public Point(int x, int y){mX=x;mY=y;}
    public Point(Point d){
        mX = d.getX();
        mY = d.getY();
    }
    public int getX(){return mX;}
    public int getY(){return mY;}
    public void setX(int x){mX=x;}
    public void setY(int y){mY=y;}

    @Override
    public boolean equals(Object o){
        Point s = (Point) o;
        if( this.mX == s.getX() ){
            if( this.mY == s.getY() )
                return true;
        }
        return false;
    }

}