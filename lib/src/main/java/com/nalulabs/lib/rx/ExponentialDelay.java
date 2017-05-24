package com.nalulabs.lib.rx;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import timber.log.Timber;

public class ExponentialDelay implements Function<Flowable<? extends Throwable>, Publisher<?>> {

    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;

    public ExponentialDelay(final int maxRetries, final int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
        retryCount = 0;
    }

    // this is a notification handler, all that is cared about here
    // is the emission "type" not emission "content"
    // only onNext triggers a re-subscription (onError + onComplete kills it)

    @Override
    public Publisher<?> apply(Flowable<? extends Throwable> inputObservable) {

        // it is critical to use inputObservable in the chain for the result
        // ignoring it and doing your own thing will break the sequence

        return inputObservable.flatMap(
                new Function<Throwable, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(@NonNull Throwable throwable) throws Exception {
                        if (++retryCount < maxRetries) {

                            // When this Observable calls onNext, the original
                            // Observable will be retried (i.e. re-subscribed)

                            long delay = (long) (Math.pow(2, retryCount - 1) * retryDelayMillis);
                            Timber.d("Retrying in %d ms", delay);

                            return Flowable.timer(delay, TimeUnit.MILLISECONDS);
                        }

                        Timber.d("Argh! i give up");

                        // Max retries hit. Pass an error so the chain is forcibly completed
                        // only onNext triggers a re-subscription (onError + onComplete kills it)
                        return Flowable.error(throwable);
                    }
                });
    }
}