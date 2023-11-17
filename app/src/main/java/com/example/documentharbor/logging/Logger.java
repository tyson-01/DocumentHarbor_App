package com.example.documentharbor.logging;

import android.util.Log;

public class Logger {
    private static final String GENERIC_TAG = "generic tag";
    public void log(String logData) {
        Log.d(GENERIC_TAG, logData);
    }

    public void log(String tag, String logData) {
        Log.d(tag, logData);
    }

    public void log(String logData, Exception e) {
        Log.d(GENERIC_TAG, logData, e);
    }

    public void log(String tag, String logData, Exception e) {
        Log.d(tag, logData, e);
    }

}
