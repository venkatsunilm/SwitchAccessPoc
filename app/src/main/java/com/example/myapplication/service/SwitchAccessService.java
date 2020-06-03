package com.example.myapplication.service;

import android.accessibilityservice.AccessibilityButtonController;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.example.myapplication.GlobalConstants;
import com.example.myapplication.SwitchAccessWindowInfo;
import com.example.myapplication.UiChangeDetector;
import com.example.myapplication.nodes.MainTreeBuilder;
import com.example.myapplication.utils.AccessibilityServiceCompatUtils;
import com.example.myapplication.utils.UiChangeStabilizer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.KeyEvent.KEYCODE_BUTTON_X;

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

        initialize();
    }

    private void initialize() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                buildTree();
//                GlobalConstants.currentNodeCompat_playPauseButton.
//                        performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS);
//                GlobalConstants.currentNodeCompat_AOSP_PLAY_PAUSE.
//                        performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS);
            }
        }, 5000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Log.i(GlobalConstants.LOGTAG, "currentNodeCompat_HMI_THREE Focused...");
                GlobalConstants.currentNodeCompat_previousButton.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS);
            }
        }, 10000);

    }

    private void buildTree() {
        new MainTreeBuilder(this).addWindowListToTree(
                SwitchAccessWindowInfo.convertZOrderWindowList(
                        AccessibilityServiceCompatUtils.getWindows(this)));
    }

    private static final HashMap<Class, WeakReference<SwitchAccessService>>
            sInstances = new HashMap<>();

    public static <T extends SwitchAccessService> T getInstanceForClass(Class clazz,
                                                                        long timeoutMillis) {
        final long timeoutTimeMillis = SystemClock.uptimeMillis() + timeoutMillis;
        while (SystemClock.uptimeMillis() < timeoutTimeMillis) {
            synchronized (sInstances) {
                final WeakReference<SwitchAccessService> ref = sInstances.get(clazz);
                if (ref != null) {
                    final T instance = (T) ref.get();
                    if (instance == null) {
                        sInstances.remove(clazz);
                    } else {
                        return instance;
                    }
                }
                try {
                    sInstances.wait(timeoutTimeMillis - SystemClock.uptimeMillis());
                } catch (InterruptedException e) {
                    return null;
                }
            }
        }
        return null;
    }

//    Log.i(LOG_TAG, "getActiveWidow: " + AccessibilityServiceCompatUtils.getActiveWidow(this));
//        Log.i(LOG_TAG, "getWindows: " + AccessibilityServiceCompatUtils.getWindows(this));
//        Log.i(LOG_TAG, "getInputFocusedNode: " + AccessibilityServiceCompatUtils.getInputFocusedNode(this));
//        Log.i(LOG_TAG, "getRootInAccessibilityFocusedWindow: " + AccessibilityServiceCompatUtils.getRootInAccessibilityFocusedWindow(this));
//        Log.i(LOG_TAG, "getRootInActiveWindow: " + AccessibilityServiceCompatUtils.getRootInActiveWindow(this));
//        Log.i(LOG_TAG, "getWindows: " + AccessibilityServiceCompatUtils.isAccessibilityButtonAvailableCompat());

    // KEYCODE_BUTTON_A, KEYCODE_BUTTON_B, KEYCODE_BUTTON_X, KEYCODE_BUTTON_Y,
    // KEYCODE_BUTTON_L1, KEYCODE_BUTTON_R1, KEYCODE_BUTTON_L2, KEYCODE_BUTTON_R2, KEYCODE_BUTTON_THUMBL, KEYCODE_BUTTON_THUMBR

    private boolean validateCurrentMusicNode(AccessibilityNodeInfoCompat currentFocusedNode) {
        if (currentFocusedNode != null && currentFocusedNode.getPackageName().equals(GlobalConstants.MYAPPPACKAGE)
                && (currentFocusedNode.getText().toString().equals(GlobalConstants.PLAYPAUSETEXT) ||
                currentFocusedNode.getText().toString().equals(GlobalConstants.PREVTEXT) ||
                currentFocusedNode.getText().toString().equals(GlobalConstants.MORETEXT) ||
                currentFocusedNode.getText().toString().equals(GlobalConstants.NEXTTEXT))) {
            return true;
        }
        return false;
    }

    private boolean validateCurrentAppTray(AccessibilityNodeInfoCompat currentFocusedNode) {
        if (currentFocusedNode.getPackageName().equals(GlobalConstants.MYAPPPACKAGE)
                && (currentFocusedNode.getText().toString().equals(GlobalConstants.HOMETEXT) ||
                currentFocusedNode.getText().toString().equals(GlobalConstants.MUSICTEXT) ||
                currentFocusedNode.getText().toString().equals(GlobalConstants.PHONETEXT) ||
                currentFocusedNode.getText().toString().equals(GlobalConstants.BACKTEXT) ||
                currentFocusedNode.getText().toString().equals(GlobalConstants.OVERVIEWTEXT))) {
            return true;
        }
        return false;
    }

    private boolean validateCurrentTEDHOURButton(AccessibilityNodeInfoCompat currentFocusedNode) {
        if (currentFocusedNode.getPackageName().equals(GlobalConstants.MYAPPPACKAGE)
                && (currentFocusedNode.getText().toString().equals(GlobalConstants.TEDTEXT) ||
                currentFocusedNode.getText().toString().equals(GlobalConstants.MIN36TEXT))) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP) {
            return false;
        }

        Log.i(GlobalConstants.LOGTAG, "Switch access service: " + event.getKeyCode());
        AccessibilityNodeInfoCompat currentFocusedNode = AccessibilityServiceCompatUtils.getInputFocusedNode(this);
        if (currentFocusedNode != null) {
            Log.i(GlobalConstants.LOGTAG, "Service currentFocusedNode text " + currentFocusedNode.getViewIdResourceName());
        }

        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case 2602:
                Toast.makeText(this, "KEYCODE_DPAD_LEFT", Toast.LENGTH_SHORT).show();
//                GlobalConstants.currentNodeCompat_phoneButton.
//                        performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS);
                // Get the focused node
                // check the package name or Parent layout name or specific node name or its id as per the requirement:
                // to test, packageName: com.example.myapplication;
                // TODO: Code refactoring and store hardcoded values into constants
                if (validateCurrentMusicNode(currentFocusedNode)
                        || validateCurrentAppTray(currentFocusedNode)
                        || validateCurrentTEDHOURButton(currentFocusedNode)) {
                    GlobalConstants.currentNodeCompat_phoneButton.
                            performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
            case 2604:
                Toast.makeText(this, "KEYCODE_DPAD_UP", Toast.LENGTH_SHORT).show();
                // Get the focused node
                // check the package name or Parent layout name or specific node name or its id as per the requirement:
                // to test, packageName: com.example.myapplication;
                // TODO: Code refactoring and store hardcoded values into constants
                Log.i(GlobalConstants.LOGTAG, "validateCurrentMusicNode: " + validateCurrentMusicNode(currentFocusedNode)
                        + " validateCurrentTEDHOURButton: " + validateCurrentTEDHOURButton(currentFocusedNode)
                        + " validateCurrentAppTray: " + validateCurrentAppTray(currentFocusedNode));

                if (validateCurrentMusicNode(currentFocusedNode)
                        || validateCurrentTEDHOURButton(currentFocusedNode)) {
                    GlobalConstants.currentNodeCompat_playPauseButton.
                            performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS);
                } else if (validateCurrentAppTray(currentFocusedNode)) {
                    GlobalConstants.currentNodeCompat_phoneButton.
                            performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case 2605:
                Toast.makeText(this, "KEYCODE_DPAD_DOWN", Toast.LENGTH_SHORT).show();
                // Get the focused node
                // check the package name or Parent layout name or specific node name or its id as per the requirement:
                // to test, packageName: com.example.myapplication;
                // TODO: Code refactoring and store hardcoded values into constants
//                Log.i(GlobalConstants.LOGTAG, "Get Parent " + currentFocusedNode.getParent());
                if (validateCurrentMusicNode(currentFocusedNode)
                        || validateCurrentAppTray(currentFocusedNode)) {
                    GlobalConstants.currentNodeCompat_MIN36.
                            performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
            case 2603:
//                Toast.makeText(this, "KEYCODE_DPAD_RIGHT", Toast.LENGTH_SHORT).show();

//                GlobalConstants.currentNodeCompat_previousButton.
//                        performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS);

                Log.i(GlobalConstants.LOGTAG, "validateCurrentMusicNode: " + validateCurrentMusicNode(currentFocusedNode)
                        + " validateCurrentTEDHOURButton: " + validateCurrentTEDHOURButton(currentFocusedNode)
                        + " validateCurrentAppTray: " + validateCurrentAppTray(currentFocusedNode));

                // Get the focused node
                // check the package name or Parent layout name or specific node name or its id as per the requirement:
                // to test, packageName: com.example.myapplication;
                // TODO: Code refactoring and store hardcoded values into constants
                if (validateCurrentAppTray(currentFocusedNode)
                        || validateCurrentMusicNode(currentFocusedNode)) {
                    GlobalConstants.currentNodeCompat_previousButton.
                            performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS);
                }

                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                break;
            case KeyEvent.KEYCODE_A:
            case KeyEvent.KEYCODE_BUTTON_L2:
                Toast.makeText(this, "KEYCODE_A/KEYCODE_BUTTON_L2", Toast.LENGTH_SHORT).show();
                // Initialize and trace screen elements.
                // TODO: Move this to some constructor in future
                new MainTreeBuilder(this).addWindowListToTree(
                        SwitchAccessWindowInfo.convertZOrderWindowList(
                                AccessibilityServiceCompatUtils.getWindows(this)));
                break;
            case KeyEvent.KEYCODE_S:
            case KeyEvent.KEYCODE_BUTTON_R2:
                Toast.makeText(this, "KEYCODE_S/KEYCODE_BUTTON_R2", Toast.LENGTH_SHORT).show();
//                TO set focus to play pause button forcefully for testing purpose
                GlobalConstants.currentNodeCompat_AOSP_PLAY_PAUSE.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS);
                break;
            case KeyEvent.KEYCODE_D:
            case KeyEvent.KEYCODE_BUTTON_X:
                Toast.makeText(this, "KEYCODE_D/KEYCODE_BUTTON_B", Toast.LENGTH_SHORT).show();
//                To set focus to Overview app tray button forcefully for testing purpose
                GlobalConstants.currentNodeCompat_AOSP_MUSIC.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS);
                break;
            case KeyEvent.KEYCODE_Q:
            case KeyEvent.KEYCODE_BUTTON_B:
                Toast.makeText(this, "KEYCODE_Q/KEYCODE_BUTTON_X", Toast.LENGTH_SHORT).show();
//                To perform a click action on the overview button of the phone app tray navigation icon.
                GlobalConstants.currentNodeCompat_AOSP_HOME.performAction(AccessibilityNodeInfoCompat.ACTION_CLICK);
                break;
            case KeyEvent.KEYCODE_W:
                Toast.makeText(this, "KEYCODE_W", Toast.LENGTH_SHORT).show();
//                To perform a click action on the overview button of the phone app tray
//                navigation icon using global available options for system ui by default.
                performGlobalAction(GLOBAL_ACTION_HOME);
                break;
        }

        return false;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i(GlobalConstants.LOGTAG, "onAccessibilityEvent: " + event);
//        Toast.makeText(this, "onAccessibilityEvent triggered.... ", Toast.LENGTH_LONG).show();

        AccessibilityNodeInfo source = event.getSource();
        if (source == null) {
            return;
        }

//        Log.i("Venk", "getPackageName: " + event.getPackageName());
//        Log.i("Venk", "getViewIdResourceName: " + source.getViewIdResourceName());

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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(GlobalConstants.LOGTAG, "onStartCommand flags: " + flags + " start id: " + startId);

//        enableService(SwitchAccessService.class);

        return super.onStartCommand(intent, flags, startId);
    }

    //    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        //TODO write your own code
//
//        return Service.START_NOT_STICKY;
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        //TODO for communication return IBinder implementation
//        return null;
//    }

//    #region Enable disable a service using test

//    private static final boolean DEBUG = false;
//    public static final int TIMEOUT_SERVICE_ENABLE = DEBUG ? Integer.MAX_VALUE : 10000;
//    public <T extends SwitchAccessService> T enableService(
//            Class<T> clazz) {
//        final String serviceName = clazz.getSimpleName();
////        final Context context = instrumentation.getContext();
//        final String enabledServices = Settings.Secure.getString(
//                getContentResolver(),
//                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
//        if (enabledServices != null) {
////            assertFalse("Service is already enabled", enabledServices.contains(serviceName));
//            Log.i(GlobalConstants.LOGTAG, "Service already enabled .......... **************** ");
//        }
//        final AccessibilityManager manager = (AccessibilityManager) getSystemService(
//                Context.ACCESSIBILITY_SERVICE);
//        final List<AccessibilityServiceInfo> serviceInfos =
//                manager.getInstalledAccessibilityServiceList();
//        for (AccessibilityServiceInfo serviceInfo : serviceInfos) {
//            final String serviceId = serviceInfo.getId();
//            if (serviceId.endsWith(serviceName)) {
//
//                Settings.Secure.putString(Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES,
//                        enabledServices + COMPONENT_NAME_SEPARATOR + serviceId)
////                ShellCommandBuilder.create(instrumentation)
////                        .putSecureSetting(Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES,
////                                enabledServices + COMPONENT_NAME_SEPARATOR + serviceId)
////                        .putSecureSetting(Settings.Secure.ACCESSIBILITY_ENABLED, "1")
////                        .run();
////
////                final T instance = getInstanceForClass(clazz, TIMEOUT_SERVICE_ENABLE);
////                if (instance == null) {
////                    ShellCommandBuilder.create(instrumentation)
////                            .putSecureSetting(Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES,
////                                    enabledServices)
////                            .run();
////                    throw new RuntimeException("Starting accessibility service " + serviceName
////                            + " took longer than " + TIMEOUT_SERVICE_ENABLE + "ms");
////                }
////                return instance;
//            }
//        }
//        throw new RuntimeException("Accessibility service " + serviceName + " not found");
//    }


//    public static void disableAllServices(Instrumentation instrumentation) {
//        final Object waitLockForA11yOff = new Object();
//        final Context context = instrumentation.getContext();
//        final AccessibilityManager manager =
//                (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
//        // Updates to manager.isEnabled() aren't synchronized
//        final AtomicBoolean accessibilityEnabled = new AtomicBoolean(manager.isEnabled());
//        manager.addAccessibilityStateChangeListener(b -> {
//            synchronized (waitLockForA11yOff) {
//                waitLockForA11yOff.notifyAll();
//                accessibilityEnabled.set(b);
//            }
//        });
//        final UiAutomation uiAutomation = instrumentation.getUiAutomation(
//                UiAutomation.FLAG_DONT_SUPPRESS_ACCESSIBILITY_SERVICES);
//        ShellCommandBuilder.create(uiAutomation)
//                .deleteSecureSetting(Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
//                .deleteSecureSetting(Settings.Secure.ACCESSIBILITY_ENABLED)
//                .run();
//        uiAutomation.destroy();
//
//        waitOn(waitLockForA11yOff, () -> !accessibilityEnabled.get(), TIMEOUT_SERVICE_ENABLE,
//                "Accessibility turns off");
//    }
//

//    #endregion

}
