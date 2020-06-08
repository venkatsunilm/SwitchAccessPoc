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
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.service.SwitchAccessService;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String SERVICE_NAME = "MFCService";
    private static final String COMPONENT_NAME_SEPARATOR = ":";
    private Button play_pause_button;
    private Button next_button;
    private Button previous_button;
    private Button now_playing_button;
    private Button Music_menu_button;
    private Button Settings_button;
    private Button more_button;
    private Button home_button;
    private Button phone_button;
    private Button overview_button;
    private Button TED_button;
    private Button NAV_button;
    private Button New_button;
    private AccessibilityManager accessibilityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play_pause_button = (Button) findViewById(R.id.btn_play_pause);
        next_button = (Button) findViewById(R.id.btn_next);
        New_button = (Button) findViewById(R.id.button_next_screen);
        previous_button = (Button) findViewById(R.id.btn_previous);
        now_playing_button = (Button) findViewById(R.id.btn_now_playing);
        Music_menu_button = (Button) findViewById(R.id.btn_music_menu);
        Settings_button = (Button) findViewById(R.id.btn_Settings);
        more_button = (Button) findViewById(R.id.btn_more);
        home_button = (Button) findViewById(R.id.btn_home);
        Button music_button = (Button) findViewById(R.id.btn_music);
        phone_button = (Button) findViewById(R.id.btn_phone);
        Button back_button = (Button) findViewById(R.id.btn_back);
        overview_button = (Button) findViewById(R.id.btn_overview);
        TED_button = (Button) findViewById(R.id.btn_TED);
        NAV_button = (Button) findViewById(R.id.btn_NAV);
        accessibilityManager = (AccessibilityManager) getSystemService(ACCESSIBILITY_SERVICE);
        Log.i(GlobalConstants.LOGTAG, "accessibilityManager: " + accessibilityManager);
        Log.i(GlobalConstants.LOGTAG, "isEnabled: " + accessibilityManager.isEnabled());
        Log.i(GlobalConstants.LOGTAG, "getInstalledAccessibilityServiceList: " + accessibilityManager.getInstalledAccessibilityServiceList());

        accessibilityManager.addAccessibilityStateChangeListener(new AccessibilityManager.AccessibilityStateChangeListener() {

            @Override
            public void onAccessibilityStateChanged(boolean enabled) {
                Log.i(GlobalConstants.LOGTAG, "MAIN ACTIVITY addAccessibilityStateChangeListener onAccessibilityStateChanged  222");
            }
        });

        // Make the activity listen to policy change events
//        CombinedPolicyProvider.get().addPolicyChangeListener(this);

        play_pause_button.requestFocus();

        New_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                enableService(SwitchAccessService.class);
            }
        }, 1000);

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

    private static final boolean DEBUG = false;
    public static final int TIMEOUT_SERVICE_ENABLE = DEBUG ? Integer.MAX_VALUE : 10000;

    public void enableService(
            Class clazz) {
        final String serviceName = SERVICE_NAME/*clazz.getSimpleName()*/;
//        final Context context = instrumentation.getContext();
        final String enabledServices = Settings.Secure.getString(
                getContentResolver(),
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

        Log.i(GlobalConstants.LOGTAG, "enabledServices: " + enabledServices);
        if (enabledServices != null) {
            Log.i(GlobalConstants.LOGTAG, "Service already enabled .......... **************** ");
            return;
        }
        final List<AccessibilityServiceInfo> serviceInfos =
                accessibilityManager.getInstalledAccessibilityServiceList();
        for (AccessibilityServiceInfo serviceInfo : serviceInfos) {
            final String serviceId = serviceInfo.getId();
            Log.i(GlobalConstants.LOGTAG, "Service id: " + serviceId);
            if (serviceId.endsWith(serviceName)) {
                Log.i(GlobalConstants.LOGTAG, "Service id: " + serviceName);
                try {
                    Settings.Secure.putString(getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES,
                            enabledServices + COMPONENT_NAME_SEPARATOR + serviceId);

                    Settings.Secure.putString(getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED,
                            "1");
                } catch (Exception ex) {
                    Log.i(GlobalConstants.LOGTAG, ex.getStackTrace().toString());
                }
            }
        }

        Log.i(GlobalConstants.LOGTAG, "manager.isEnabled(): " + accessibilityManager.isEnabled());
    }


    private void disableAllServices() {
        final List<AccessibilityServiceInfo> serviceInfos =
                accessibilityManager.getInstalledAccessibilityServiceList();
        for (AccessibilityServiceInfo serviceInfo : serviceInfos) {
            Settings.Secure.putString(getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED,
                    "0");
        }
    }

}
