package com.nalulabs.lib.mvp;

/**
 * Created by jack on 10/04/17.
 */

public interface ViewHolderFactory<T1, T2> {

    T1 newInstance(T2 binding);
}
