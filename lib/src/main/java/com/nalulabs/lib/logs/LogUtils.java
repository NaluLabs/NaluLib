package com.nalulabs.lib.logs;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.adapter.rxjava2.HttpException;
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
                if (isConnectionError(e)) {
                    // fine, irrelevant network problem or API that throws on cancellation
                    return;
                }
                if (e instanceof InterruptedException) {
                    // fine, some blocking code was interrupted by a dispose call
                    return;
                }
            }
            Thread.currentThread().getUncaughtExceptionHandler()
                    .uncaughtException(Thread.currentThread(), e);
        });
    }

    public static boolean isConnectionError(Throwable t)
    {
        if (t instanceof UnknownHostException || t instanceof SocketException || t instanceof SocketTimeoutException
                || t instanceof HttpException)
        {
            return true;
        }
        return false;
    }
}
