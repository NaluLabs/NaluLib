package com.nalulabs.lib.logs;

import io.reactivex.annotations.NonNull;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.functions.Consumer;

/**
 * Created by jack on 22/05/17.
 */
public class LogErrorHandler implements Consumer<Throwable> {
    @Override
    public void accept(@NonNull Throwable e) throws Exception {
        if (e instanceof UndeliverableException) {
            e = e.getCause();
            if (LogUtils.isConnectionError(e)) {
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
    }
}
