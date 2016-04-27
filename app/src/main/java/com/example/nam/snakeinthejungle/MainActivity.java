package com.example.nam.snakeinthejungle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    GestureDetector mDetector;
    SnakeView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDetector = new GestureDetector(this, mGestureListener);
        sv = new SnakeView(this);


        setContentView(sv);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("Ent","Touch");
        return mDetector.onTouchEvent(event);
    }

    GestureDetector.OnGestureListener mGestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("Ent","onDown");
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d("Ent","onShowPress");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d("Ent","onSingleTapUp");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("Ent","onScroll");
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d("Ent","onLongPress");

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float dx, dy;

            dx = e2.getX()-e1.getX();
            dy = e2.getY()-e1.getY();

            if( Math.abs(dx) >  Math.abs(dy) ){
                Log.d("Ent","가로방향 플립");
                if( dx > 0 ) {
                    sv.Right();
                    Log.d("Ent", "Right");
                }
                else {
                    sv.Left();
                    Log.d("Ent", "Left");
                }
            }
            else{
                Log.d("Ent","세로방향 플립");
                if(dy>0) {
                    sv.Down();
                    Log.d("Ent", "Down");
                }
                else {
                    sv.Up();
                    Log.d("Ent", "Up");
                }
            }

            Log.d("Ent","onFling");

            return false;
        }
    };
}