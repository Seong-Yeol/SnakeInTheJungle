package com.example.nam.snakeinthejungle;

import android.app.Activity;
import android.content.Context;
import android.media.MediaDataSource;
import android.media.MediaPlayer;

/**
 * Created by Nam on 2016-05-03.
 */
public class AudioManager {
    private MediaPlayer mBGM;
    private MediaPlayer mEatingSound;

    private boolean onBgm = true;
    private boolean onEffect = true;
    
    public AudioManager(Context context){
        mBGM = MediaPlayer.create(context, R.raw.dubakupado);
        mEatingSound = MediaPlayer.create(context, R.raw.eating_sound);
        mBGM.seekTo(0);
        mBGM.setLooping(true);
    }
    
    public void pause(){
        if( mBGM.isPlaying() )
            mBGM.pause();

    }
    
    public void start(){
        if( mBGM != null ){
            if( !mBGM.isPlaying() && onBgm )
                mBGM.start();
        }
    }

    public void eatSound(){
        if( onEffect ) {
            mEatingSound.seekTo(0);
            mEatingSound.start();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if(mBGM != null ){
            mBGM.release();
            mBGM = null;
        }
    }

    
}
