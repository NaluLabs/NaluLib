package com.nalulabs.testlib;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class TrampolineSchedulerRule implements TestRule
{

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

    @Override
    public Statement apply(final Statement base, Description description)
    {
        return new Statement()
        {
            @Override
            public void evaluate() throws Throwable
            {
                RxJavaPlugins.setIoSchedulerHandler(TRAMPOLINE_FUNCTION);
                RxJavaPlugins.setComputationSchedulerHandler(TRAMPOLINE_FUNCTION);
                RxJavaPlugins.setNewThreadSchedulerHandler(TRAMPOLINE_FUNCTION);
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(TRAMPOLINE_CALLABLE_FUNCTION);

                try
                {
                    base.evaluate();
                } finally
                {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }
}