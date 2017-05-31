package com.nalulabs.lib.logs;

import com.nalulabs.lib.mvp.ManagedException;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.exceptions.CompositeException;
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

//        if (debug) {
//            Thread.setDefaultUncaughtExceptionHandler((paramThread, paramThrowable) ->
//            {
//                Timber.e(paramThrowable);
//                System.exit(2); //Prevents the service/app from freezing
//            });
//        }

        RxJavaPlugins.setErrorHandler(new LogErrorHandler());
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

    public static boolean isExceptionToBeLogged(Throwable t) {
        if (t instanceof ManagedException || isConnectionError(t)) {
            return false;
        }
        if (t instanceof CompositeException) {
            return existsExceptionToBeLogged(((CompositeException) t).getExceptions());
        }
        return true;
    }

    private static boolean existsExceptionToBeLogged(List<Throwable> exceptions) {
        for (Throwable t : exceptions) {
            if (isExceptionToBeLogged(t)) {
                return true;
            }
        }
        return false;
    }
}
