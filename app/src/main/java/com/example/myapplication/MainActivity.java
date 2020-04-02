package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.i("Venk", "Key ACTION: " + event.getKeyCode());

        if (event.getAction() == KeyEvent.ACTION_DOWN){
        }
         else if (event.getAction() == KeyEvent.ACTION_UP){

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
