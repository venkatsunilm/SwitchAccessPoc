package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button play_pause_button, next_button, previous_button,
            now_playing_button, Music_menu_button, Settings_button,
            more_button, home_button, music_button, phone_button, back_button, overview_button, TED_button, NAV_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play_pause_button = (Button) findViewById(R.id.btn_play_pause);
        next_button = (Button) findViewById(R.id.btn_next);
        previous_button = (Button) findViewById(R.id.btn_previous);
        now_playing_button = (Button) findViewById(R.id.btn_now_playing);
        Music_menu_button = (Button) findViewById(R.id.btn_music_menu);
        Settings_button = (Button) findViewById(R.id.btn_Settings);
        more_button = (Button) findViewById(R.id.btn_more);
        home_button = (Button) findViewById(R.id.btn_home);
        music_button = (Button) findViewById(R.id.btn_music);
        phone_button = (Button) findViewById(R.id.btn_phone);
        back_button = (Button) findViewById(R.id.btn_back);
        overview_button = (Button) findViewById(R.id.btn_overview);
        TED_button = (Button) findViewById(R.id.btn_TED);
        NAV_button = (Button) findViewById(R.id.btn_NAV);
        play_pause_button.requestFocus();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        play_pause_button.requestFocus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        play_pause_button.requestFocus();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP) {
            return false;
        }

//        Log.i(GlobalConstants.LOGTAG, "Main Activity dispatchKeyEvent keycode: " + event.getKeyCode());

        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_K:
//                play_pause_button.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED);
                break;
//                Using L key to do lLEFT ROTATE traversal
            case KeyEvent.KEYCODE_L:
            case KeyEvent.KEYCODE_BUTTON_L1:
                Toast.makeText(this, "KEYCODE_L/KEYCODE_BUTTON_L1", Toast.LENGTH_SHORT).show();
//                TODO: Refactoring to be done, this is a test sample
                switch (getCurrentFocus().getId()) {
                    case R.id.btn_play_pause:
                        previous_button.requestFocus();
                        break;
                    case R.id.btn_previous:
                        Music_menu_button.requestFocus();
                        break;
                    case R.id.btn_music_menu:
                        now_playing_button.requestFocus();
                        break;
                    case R.id.btn_Settings:
                        more_button.requestFocus();
                        break;
                    case R.id.btn_more:
                        next_button.requestFocus();
                        break;
                    case R.id.btn_next:
                        play_pause_button.requestFocus();
                        break;
                    case R.id.btn_music:
                        home_button.requestFocus();
                        break;
                    case R.id.btn_phone:
                        music_button.requestFocus();
                        break;
                    case R.id.btn_back:
                        phone_button.requestFocus();
                        break;
                    case R.id.btn_overview:
                        back_button.requestFocus();
                        break;
                    case R.id.btn_TED:
                        NAV_button.requestFocus();
                        break;
                    case R.id.btn_NAV:
                        TED_button.requestFocus();
                        break;
                }

                break;
            case KeyEvent.KEYCODE_R:
            case KeyEvent.KEYCODE_BUTTON_R1:
                Toast.makeText(this, "KEYCODE_R/KEYCODE_BUTTON_R1", Toast.LENGTH_SHORT).show();
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    return super.dispatchKeyEvent(event);
                }
//                TODO: Refactoring to be done, this is a test sample
                switch (getCurrentFocus().getId()) {
                    case R.id.btn_play_pause:
                        next_button.requestFocus();
                        break;
                    case R.id.btn_next:
                        more_button.requestFocus();
                        break;
                    case R.id.btn_more:
                        Settings_button.requestFocus();
                        break;
                    case R.id.btn_now_playing:
                        Music_menu_button.requestFocus();
                        break;
                    case R.id.btn_music_menu:
                        previous_button.requestFocus();
                        break;
                    case R.id.btn_previous:
                        play_pause_button.requestFocus();
                        break;
                    case R.id.btn_home:
                        music_button.requestFocus();
                        break;
                    case R.id.btn_music:
                        phone_button.requestFocus();
                        break;
                    case R.id.btn_phone:
                        back_button.requestFocus();
                        break;
                    case R.id.btn_back:
                        overview_button.requestFocus();
                        break;
                    case R.id.btn_TED:
                        NAV_button.requestFocus();
                        break;
                    case R.id.btn_NAV:
                        TED_button.requestFocus();
                        break;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                break;
        }
        return false;
    }
}
