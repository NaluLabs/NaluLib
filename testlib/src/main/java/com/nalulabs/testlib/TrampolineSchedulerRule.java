package com.nalulabs.testlib;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class TrampolineSchedulerRule extends TestWatcher {

    private static final Function<Scheduler, Scheduler> TRAMPOLINE_FUNCTION = new Function<Scheduler, Scheduler>() {
        @Override
        public Scheduler apply(@NonNull Scheduler scheduler) throws Exception {
            return Schedulers.trampoline();
        }
    };

    private static final Function<Callable<Scheduler>, Scheduler> TRAMPOLINE_CALLABLE_FUNCTION = new Function<Callable<Scheduler>, Scheduler>() {
        @Override
        public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable) throws Exception {
            return Schedulers.trampoline();
        }
    };

    @Override protected void starting(Description description) {
        RxJavaPlugins.setIoSchedulerHandler(TRAMPOLINE_FUNCTION);
        RxJavaPlugins.setComputationSchedulerHandler(TRAMPOLINE_FUNCTION);
        RxJavaPlugins.setNewThreadSchedulerHandler(TRAMPOLINE_FUNCTION);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(TRAMPOLINE_CALLABLE_FUNCTION);
    }

    @Override protected void finished(Description description) {
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }
}