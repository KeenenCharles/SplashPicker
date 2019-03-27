package com.kc.splashpicker;

import android.app.Activity;
import android.content.Intent;

import androidx.fragment.app.Fragment;

public class SplashPicker {

    public static final int PICK_IMAGE_REQUEST = 0x4;
    public static final String KEY_IMAGE = "IMAGE";

    public static void open(Activity activity, String clientID){
        open(activity, clientID, null);
    }

    public static void open(Fragment fragment, Activity activity, String clientID){
        open(fragment, activity, clientID, null);
    }

    public static void open(Activity activity, String clientID, String title){
        Intent intent = getIntent(activity, clientID, title);
        activity.startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    public static void open(Fragment fragment, Activity activity, String clientID, String title){
        Intent intent = getIntent(activity, clientID, title);
        fragment.startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private static Intent getIntent(Activity activity, String clientID, String title) {
        Intent intent = new Intent(activity, PickerActivity.class);
        intent.putExtra("CLIENT_ID", clientID);
        intent.putExtra("TITLE", title == null ? "Select Image" : title);
        return intent;
    }
}
