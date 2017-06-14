package com.nalulabs.testlib;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

public class TestSchedulerRule extends TestWatcher {

    private final TestScheduler testScheduler = new TestScheduler();

    public TestScheduler getTestScheduler() {
        return testScheduler;
    }

    private final Function<Scheduler, Scheduler> TEST_SCHEDULER_FUNCTION = new Function<Scheduler, Scheduler>() {
        @Override
        public Scheduler apply(@NonNull Scheduler scheduler) throws Exception {
            return testScheduler;
        }
    };

    private static final Function<Callable<Scheduler>, Scheduler> TRAMPOLINE_CALLABLE_FUNCTION = new Function<Callable<Scheduler>, Scheduler>() {
        @Override
        public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable) throws Exception {
            return Schedulers.trampoline();
        }
    };

    @Override protected void starting(Description description) {
        RxJavaPlugins.setIoSchedulerHandler(TEST_SCHEDULER_FUNCTION);
        RxJavaPlugins.setComputationSchedulerHandler(TEST_SCHEDULER_FUNCTION);
        RxJavaPlugins.setNewThreadSchedulerHandler(TEST_SCHEDULER_FUNCTION);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(TRAMPOLINE_CALLABLE_FUNCTION);
    }

    @Override protected void finished(Description description) {
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }

    public void advanceTimeBy(long delayTime, TimeUnit unit) {
        testScheduler.advanceTimeBy(delayTime, unit);
    }

    public void advanceTimeTo(long delayTime, TimeUnit unit) {
        testScheduler.advanceTimeTo(delayTime, unit);
    }

    public void triggerActions() {
        testScheduler.triggerActions();
    }
}