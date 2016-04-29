package com.example.nam.snakeinthejungle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class TitleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        //findViewById(R.id.new_game_button).setOnClickListener(mClickListener);
    }

    public void onClickNewGame(View v){
        Log.d("NamD","activted onClickNewGame method");

        Intent intentGameActivity =
                new Intent(TitleActivity.this, GameActivity.class);
        startActivity(intentGameActivity);
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.new_game_button:
                    Log.d("NamD","activted case in onClick method");
                    break;
/*                case R.id.button_session:
                    Log.d("OnClickListener", "click session button");
                    // 액티비티 실행
                    Intent intentSubActivity =
                            new Intent(MainActivity.this, SessionActivity.class);
                    startActivity(intentSubActivity);
                    break;*/
            }
        }


    };

}
