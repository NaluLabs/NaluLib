package com.nalulabs.lib.logs;

import java.io.IOException;

import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;

/**
 * Created by fabiocollini on 10/05/17.
 */

public class LogUtils
{
    public static void initLoggingAndCrashReporting(boolean debug)
    {
        Timber.plant(debug
                ? new Timber.DebugTree()
                : new CrashReportingTree());

        if (debug) {
            Thread.setDefaultUncaughtExceptionHandler((paramThread, paramThrowable) ->
            {
                Timber.e(paramThrowable);
                System.exit(2); //Prevents the service/app from freezing
            });
        }

        RxJavaPlugins.setErrorHandler(e -> {
            if (e instanceof UndeliverableException) {
                e = e.getCause();
            }
            if (e instanceof IOException) {
                // fine, irrelevant network problem or API that throws on cancellation
                return;
            }
            if (e instanceof InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return;
            }
            if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
                // that's likely a bug in the application
                Thread.currentThread().getUncaughtExceptionHandler()
                        .uncaughtException(Thread.currentThread(), e);
                return;
            }
            if (e instanceof IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().getUncaughtExceptionHandler()
                        .uncaughtException(Thread.currentThread(), e);
                return;
            }
            Timber.w("Undeliverable exception received, not sure what to do", e);
        });
    }
}
