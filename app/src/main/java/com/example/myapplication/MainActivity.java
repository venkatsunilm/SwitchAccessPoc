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

public class MainActivity extends AppCompatActivity {

    private Button play_pause_button, next_button, previous_button,
            now_playing_button, Music_menu_button, Settings_button,
            more_button,home_button,music_button,phone_button,back_button
            ,overview_button,TED_button,NAV_button;

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
        music_button=(Button) findViewById(R.id.btn_music);
        phone_button = (Button)findViewById(R.id.btn_phone);
        back_button = (Button)findViewById(R.id.btn_back);
        overview_button = (Button)findViewById(R.id.btn_overview);
        TED_button = (Button)findViewById(R.id.btn_TED);
        NAV_button = (Button)findViewById(R.id.btn_NAV);
        play_pause_button.requestFocus(0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {


//        play_pause_button.requestFocus();
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.i("Venk", "dispatchKeyEvent: " + event);

        if (event.getAction() == KeyEvent.ACTION_UP) {
            return super.dispatchKeyEvent(event);
        }

        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_DPAD_UP:
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                break;
            case KeyEvent.KEYCODE_K:

//
//                if (getCurrentFocus() == play_pause_button) {
//
//                }
//
//                play_pause_button.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED);
//                play_pause_button.setNextFocusForwardId(R.id.btn_next);

                break;
            case KeyEvent.KEYCODE_L:
                Log.i(GlobalConstants.LOGTAG, "getCurrentFocus: " + getCurrentFocus().getId() + " btn_play_pause: " + R.id.btn_play_pause);


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
//                    case R.id.btn_top:
//                        Settings_button.requestFocus();
//                        break;
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

//                    case R.id.btn_home:
//                        music_button.requestFocus();
//                        break;

                    case R.id.btn_TED:
                        NAV_button.requestFocus();
                        break;
                    case R.id.btn_NAV:
                        TED_button.requestFocus();
                        break;



                }

                break;


            case KeyEvent.KEYCODE_R:
                Log.i(GlobalConstants.LOGTAG, "getCurrentFocus: " + getCurrentFocus().getId() + " btn_play_pause: " + R.id.btn_play_pause);

                if (event.getAction() == KeyEvent.ACTION_UP) {
                    return super.dispatchKeyEvent(event);
                }

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
//                    case R.id.btn_last:
//                        now_playing_button.requestFocus();
//                        break;
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
        }
        return super.dispatchKeyEvent(event);
    }

    //    override fun dispatchKeyEvent(event: KeyEvent): Boolean { //   Log.i("mfc","hi.."+event.getAction());
////        Toast.makeText(this@MainActivity, "dispatchKeyEvent:" + event.keyCode, Toast.LENGTH_SHORT).show()
//        Log.i("Venk", "Key pressed " + event.keyCode)
//
//        val windowsInfo = AccessibilityWindowInfo.obtain()
//        Log.i("Venk", "dispatchKeyEvent windowsInfo " + windowsInfo)
//
//
//        if (event.action == KeyEvent.ACTION_DOWN) {
//            Log.i("Venk", "Key ACTION_DOWN " + event.keyCode)
////            Toast.makeText(this@MainActivity, "KeyDown:" + event.keyCode, Toast.LENGTH_SHORT)
////                .show()
//            if (event.keyCode == 190) {
//                val intent = Intent(Intent.ACTION_MAIN, null)
//                intent.addCategory(Intent.CATEGORY_LAUNCHER)
//                val cn = ComponentName(
//                        "com.example.myapplication",
//                        "com.example.myapplication.MainActivity"
//                )
//                intent.component = cn
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                try {
//                    startActivity(intent)
//                } catch (e: ActivityNotFoundException) {
////                    Toast.makeText(this@MainActivity, "Activity Not Found", Toast.LENGTH_SHORT)
////                        .show()
//                }
//            }
//        } else if (event.action == KeyEvent.ACTION_UP) {
//            Log.i("Venk", "Key ACTION_UP " + event.keyCode)
////            Toast.makeText(this@MainActivity, "KeyUp:" + event.keyCode, Toast.LENGTH_SHORT)
////                .show()
//        }
//        return true
//    }

}
