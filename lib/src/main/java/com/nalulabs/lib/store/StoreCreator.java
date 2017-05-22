package com.nalulabs.lib.store;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.nytimes.android.external.fs2.FileSystemRecordPersister;
import com.nytimes.android.external.fs2.PathResolver;
import com.nytimes.android.external.fs2.filesystem.FileSystemFactory;
import com.nytimes.android.external.store2.base.Fetcher;
import com.nytimes.android.external.store2.base.Persister;
import com.nytimes.android.external.store2.base.impl.MemoryPolicy;
import com.nytimes.android.external.store2.base.impl.RealStoreBuilder;
import com.nytimes.android.external.store2.base.impl.Store;
import com.nytimes.android.external.store2.base.impl.StoreBuilder;
import com.nytimes.android.external.store2.middleware.GsonParserFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import okio.BufferedSource;

public class StoreCreator
{

    private final Gson gson;
    private final Application application;

    public StoreCreator(Gson gson, Application application)
    {
        this.gson = gson;
        this.application = application;
    }

    @NonNull
    public <T, V> Store<T, V> createStore(Fetcher<ResponseBody, V> fetcher, PathResolver<V> pathResolver, Type type, int expirationDuration, TimeUnit timeUnit)
    {
        Persister<BufferedSource, V> persister = createPersister(application, pathResolver, expirationDuration, timeUnit);

        MemoryPolicy memoryPolicy = MemoryPolicy.builder()
                .setExpireAfter(expirationDuration)
                .setExpireAfterTimeUnit(timeUnit)
                .build();

        RealStoreBuilder<BufferedSource, T, V> builder = StoreBuilder.<V, BufferedSource, T>
                parsedWithKey()
                .memoryPolicy(memoryPolicy)
                .fetcher(s -> fetcher.fetch(s).map(ResponseBody::source))
                .parser(GsonParserFactory.createSourceParser(gson, type));

        if (persister != null)
        {
            builder = builder.persister(persister);
        }

        return builder.open();
    }

    @Nullable
    private <V> Persister<BufferedSource, V> createPersister(Application application, PathResolver<V> pathResolver, int expirationDuration, TimeUnit days)
    {
        try
        {
            return FileSystemRecordPersister.create(FileSystemFactory.create(application.getFilesDir()), pathResolver, expirationDuration, days);
        } catch (IOException e)
        {
            return null;
        }
    }

}
