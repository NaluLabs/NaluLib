package com.nalulabs.nalulib.example;

import org.parceler.Parcel;

/**
 * Created by fabiocollini on 24/05/17.
 */

@Parcel
public class Item2 extends BaseObject {
    public String text;

    public Item2(String text) {
        this.text = text;
    }

    public Item2() {
    }
}
