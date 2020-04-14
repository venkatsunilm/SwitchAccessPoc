package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;

import com.example.myapplication.nodes.MainTreeBuilder;
import com.example.myapplication.utils.AccessibilityServiceCompatUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_DPAD_UP:
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                break;
            case KeyEvent.KEYCODE_N:
                Button play_pause_button = (Button) findViewById(R.id.btn_play_pause);
                play_pause_button.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED);
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
