package com.example.myapplication;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String SERVICE_NAME = "MFCService";
    private static final String COMPONENT_NAME_SEPARATOR = ":";
    private Button play_pause_button;
    private Button next_button;
    private Button previous_button;
    private Button now_playing_button;
    private Button music_menu_button;
    private Button settings_button;
    private Button more_button;
    private Button home_button;
    private Button phone_button;
    private Button overview_button;
    private Button TED_button;
    private Button NAV_button;
    private Button new_button;
    private AccessibilityManager accessibilityManager;
    private Button music_button;
    private Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play_pause_button = (Button) findViewById(R.id.btn_play_pause);
        next_button = (Button) findViewById(R.id.btn_next);
        new_button = (Button) findViewById(R.id.button_next_screen);
        previous_button = (Button) findViewById(R.id.btn_previous);
        now_playing_button = (Button) findViewById(R.id.btn_now_playing);
        music_menu_button = (Button) findViewById(R.id.btn_music_menu);
        settings_button = (Button) findViewById(R.id.btn_Settings);
        more_button = (Button) findViewById(R.id.btn_more);
        home_button = (Button) findViewById(R.id.btn_home);
        music_button = (Button) findViewById(R.id.btn_music);
        phone_button = (Button) findViewById(R.id.btn_phone);
        back_button = (Button) findViewById(R.id.btn_back);
        overview_button = (Button) findViewById(R.id.btn_overview);
        TED_button = (Button) findViewById(R.id.btn_TED);
        NAV_button = (Button) findViewById(R.id.btn_NAV);
        accessibilityManager = (AccessibilityManager) getSystemService(ACCESSIBILITY_SERVICE);
        Log.i(GlobalConstants.LOGTAG, "accessibilityManager: " + accessibilityManager);

        accessibilityManager.addAccessibilityStateChangeListener(new AccessibilityManager.AccessibilityStateChangeListener() {

            @Override
            public void onAccessibilityStateChanged(boolean enabled) {
                Log.i(GlobalConstants.LOGTAG, "MAIN ACTIVITY addAccessibilityStateChangeListener ");
            }
        });

//        home_button.setFocusedByDefault(false);

        new_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });


        if (!GlobalConstants.IS_ANDROID_PHONE) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    enableService();
                }
            }, 3000);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//                music_button.clearFocus();
//                play_pause_button.clearFocus();

//                music_button.performAccessibilityAction(
//                        AccessibilityNodeInfo.ACTION_CLEAR_FOCUS, null);
//                home_button.setFocusable(false);
//                music_button.setFocusable(false);
//                phone_button.setFocusable(false);
//                back_button.setFocusable(false);
//                overview_button.setFocusable(false);
//                now_playing_button.setFocusable(false);
//                music_menu_button.setFocusable(false);
//                new_button.setFocusable(false);
//                settings_button.setFocusable(false);
//                previous_button.setFocusable(false);
//                play_pause_button.setFocusable(false);
//                next_button.setFocusable(false);
//                more_button.setFocusable(false);
//                TED_button.setFocusable(false);
//                NAV_button.setFocusable(false);

//                home_button.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
//                play_pause_button.performAccessibilityAction(
//                        AccessibilityNodeInfo.ACTION_FOCUS, null);

                Log.i(GlobalConstants.LOGTAG, "Removing focus ...........");
            }
        }, 10000);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        play_pause_button.requestFocus();
    }

    @Override
    protected void onResume() {
        Log.i(GlobalConstants.LOGTAG, "On Resume.........");
        super.onResume();
//        play_pause_button.requestFocus();
    }

    private static final boolean DEBUG = false;
    public static final int TIMEOUT_SERVICE_ENABLE = DEBUG ? Integer.MAX_VALUE : 10000;

    public void enableService() {
        final String serviceName = SERVICE_NAME;
//        final Context context = instrumentation.getContext();
        final String enabledServices = Settings.Secure.getString(
                getContentResolver(),
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        if (!GlobalConstants.IS_HARDWARE && enabledServices != null) {
            Log.i(GlobalConstants.LOGTAG, "enabledServices not null, already enabled " + enabledServices);
            return;
        }
        final List<AccessibilityServiceInfo> serviceInfos =
                accessibilityManager.getInstalledAccessibilityServiceList();

//        Log.i(GlobalConstants.LOGTAG, "serviceInfos: " + serviceInfos);

        for (AccessibilityServiceInfo serviceInfo : serviceInfos) {
            final String serviceId = serviceInfo.getId();
            Log.i(GlobalConstants.LOGTAG, "Service id: " + serviceId);
            if (serviceId.endsWith(serviceName)) {
                Log.i(GlobalConstants.LOGTAG, "Service id: " + serviceName);
                try {
                    Settings.Secure.putString(getContentResolver(),
                            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES,
                            enabledServices + COMPONENT_NAME_SEPARATOR + serviceId);
                    Settings.Secure.putString(getContentResolver(),
                            Settings.Secure.ACCESSIBILITY_ENABLED,
                            "1");
                } catch (Exception ex) {
                    Log.i(GlobalConstants.LOGTAG, ex.getStackTrace().toString());
                }
            }
        }

    }


    private void disableAllServices() {
        final List<AccessibilityServiceInfo> serviceInfos =
                accessibilityManager.getInstalledAccessibilityServiceList();
        for (AccessibilityServiceInfo serviceInfo : serviceInfos) {
            Settings.Secure.putString(getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED,
                    "0");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(GlobalConstants.LOGTAG, "On onStart.........");

    }
}
