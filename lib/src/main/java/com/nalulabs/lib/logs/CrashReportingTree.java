package com.nalulabs.lib.logs;

import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import timber.log.Timber;

public class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable throwable) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            FirebaseCrash.logcat(priority, tag, message);

            if (throwable != null) {
                FirebaseCrash.report(throwable);
            }
        }
    }