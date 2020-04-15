package com.example.myapplication.service;

import android.accessibilityservice.AccessibilityButtonController;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.example.myapplication.GlobalConstants;
import com.example.myapplication.SwitchAccessWindowInfo;
import com.example.myapplication.UiChangeDetector;
import com.example.myapplication.nodes.MainTreeBuilder;
import com.example.myapplication.utils.AccessibilityServiceCompatUtils;
import com.example.myapplication.utils.UiChangeStabilizer;

import java.util.ArrayList;
import java.util.List;

public class SwitchAccessService extends AccessibilityService {

    private final String LOG_TAG = "Venk";
    private AccessibilityButtonController accessibilityButtonController;
    private AccessibilityButtonController
            .AccessibilityButtonCallback accessibilityButtonCallback;
    AccessibilityServiceInfo info;
//    AccessibilityNodeInfo nodeInfo;
//    AccessibilityWindowInfo windowInfo;

    List<AccessibilityNodeInfo> childNodeInfo = new ArrayList<>();


    @Nullable
    private static SwitchAccessService instance;
    private UiChangeDetector eventProcessor;
    private UiChangeStabilizer uiChangeStabilizer;

    public static SwitchAccessService getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        info = getServiceInfo();

        //        region: These configurations are done using the xml, so this is not in use now

        // Set the type of events that this service wants to listen to. Others
        // won't be passed to this service.

        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED |
                AccessibilityEvent.TYPE_VIEW_FOCUSED |
                AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED |
                AccessibilityEvent.TYPE_VIEW_SELECTED;
//                AccessibilityEvent.TYPES_ALL_MASK |
//                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED |
//                AccessibilityEvent.TYPE_WINDOWS_CHANGED |
//                AccessibilityEvent.TYPE_TOUCH_INTERACTION_START |
//                AccessibilityEvent.TYPE_GESTURE_DETECTION_START |
//                AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START |
//                AccessibilityEvent.TYPE_VIEW_SCROLLED;

        // If you only want this service to work with specific applications, set their
        // package names here. Otherwise, when the service is activated, it will listen
        // to events from all applications.
//        info.packageNames = new String[]
//                {"com.gm.hmi.switchaccess"};

        // Set the type of feedback your service will provide.
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;

        // Default services are invoked only if no package-specific ones are present
        // for the type of AccessibilityEvent generated. This service *is*
        // application-specific, so the flag isn't necessary. If this was a
        // general-purpose service, it would be worth considering setting the
        // DEFAULT flag.

        info.flags = AccessibilityServiceInfo.DEFAULT;
//        info.flags

        info.notificationTimeout = 100;

//        this.setServiceInfo(info);

//        endregion

        uiChangeStabilizer = new UiChangeStabilizer();
        eventProcessor = new UiChangeDetector(uiChangeStabilizer);

        instance = this;

        // get the active window information
        Log.i(LOG_TAG, "onServiceConnected.... ");
    }

//    Log.i(LOG_TAG, "getActiveWidow: " + AccessibilityServiceCompatUtils.getActiveWidow(this));
//        Log.i(LOG_TAG, "getWindows: " + AccessibilityServiceCompatUtils.getWindows(this));
//        Log.i(LOG_TAG, "getInputFocusedNode: " + AccessibilityServiceCompatUtils.getInputFocusedNode(this));
//        Log.i(LOG_TAG, "getRootInAccessibilityFocusedWindow: " + AccessibilityServiceCompatUtils.getRootInAccessibilityFocusedWindow(this));
//        Log.i(LOG_TAG, "getRootInActiveWindow: " + AccessibilityServiceCompatUtils.getRootInActiveWindow(this));
//        Log.i(LOG_TAG, "getWindows: " + AccessibilityServiceCompatUtils.isAccessibilityButtonAvailableCompat());

    // KEYCODE_BUTTON_A, KEYCODE_BUTTON_B, KEYCODE_BUTTON_X, KEYCODE_BUTTON_Y,
    // KEYCODE_BUTTON_L1, KEYCODE_BUTTON_R1, KEYCODE_BUTTON_L2, KEYCODE_BUTTON_R2, KEYCODE_BUTTON_THUMBL, KEYCODE_BUTTON_THUMBR

    private boolean validateCurrentNode(AccessibilityNodeInfoCompat currentFocusedNode) {
        if (currentFocusedNode.getPackageName().equals("com.example.myapplication")
                && (currentFocusedNode.getText().toString().contains("PLAY_PAUSE") ||
                currentFocusedNode.getText().toString().contains("next") ||
                currentFocusedNode.getText().toString().contains("more") ||
                currentFocusedNode.getText().toString().contains("prev"))) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        Log.i(LOG_TAG, "onKeyEvent: " + event);
        AccessibilityNodeInfoCompat currentFocusedNode = AccessibilityServiceCompatUtils.getInputFocusedNode(this);
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                // Get the focused node
                // check the package name or Parent layout name or specific node name or its id as per the requirement:
                // to test, packageName: com.example.myapplication;
                // TODO: Code refactoring and store hardcoded values into constants
                Log.i(GlobalConstants.LOGTAG, "Get Parent " + currentFocusedNode.getParent());
                if (validateCurrentNode(currentFocusedNode)) {
                    GlobalConstants.currentNodeCompat_HomeMain.performAction(AccessibilityNodeInfoCompat.ACTION_CLICK);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                // Get the focused node
                // check the package name or Parent layout name or specific node name or its id as per the requirement:
                // to test, packageName: com.example.myapplication;
                // TODO: Code refactoring and store hardcoded values into constants
                Log.i(GlobalConstants.LOGTAG, "Get Parent " + currentFocusedNode.getParent());
                if (validateCurrentNode(currentFocusedNode)) {
                    GlobalConstants.currentNodeCompat_phoneButton.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_A:
                new MainTreeBuilder(this).addWindowListToTree(
                        SwitchAccessWindowInfo.convertZOrderWindowList(
                                AccessibilityServiceCompatUtils.getWindows(this)));
                break;
            case KeyEvent.KEYCODE_S:
                GlobalConstants.currentNodeCompat_playPauseButton.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS);
                break;
            case KeyEvent.KEYCODE_D:
                GlobalConstants.currentNodeCompat_musicButton.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS);
                break;
            case KeyEvent.KEYCODE_Q:
                GlobalConstants.currentNodeCompat_OverviewMain.performAction(AccessibilityNodeInfoCompat.ACTION_CLICK);
                break;
            case KeyEvent.KEYCODE_W:
                performGlobalAction(GLOBAL_ACTION_HOME);
                break;
        }

        return super.onKeyEvent(event);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i(GlobalConstants.LOGTAG, "onAccessibilityEvent: " + event);

        AccessibilityNodeInfo source = event.getSource();
        if (source == null) {
            return;
        }

        Log.i("Venk", "getPackageName: " + event.getPackageName());
        Log.i("Venk", "getViewIdResourceName: " + source.getViewIdResourceName());

        if (eventProcessor != null) {
            eventProcessor.onAccessibilityEvent(event);
        }
    }

    @Override
    protected boolean onGesture(int gestureId) {
        Log.i(LOG_TAG, "onGesture: " + gestureId);
        return super.onGesture(gestureId);
    }

    @Override
    public void onInterrupt() {
        Log.d(LOG_TAG, "onInterrupt: .");
    }
}
