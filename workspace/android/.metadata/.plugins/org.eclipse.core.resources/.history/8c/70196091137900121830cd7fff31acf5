package com.tsegaab.besso;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {

    static final String SERVER_URL = "http://10.5.23.149:8080"; 
    static final String SENDER_ID = "987765716293"; 
    static final String TAG = "AndroidHive GCM";

    static final String DISPLAY_MESSAGE_ACTION =
            "com.tsegaab.besso.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";
    
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}