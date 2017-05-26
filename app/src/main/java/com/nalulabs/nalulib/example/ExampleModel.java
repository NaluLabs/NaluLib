package com.nalulabs.nalulib.example;

import com.nalulabs.lib.mvp.BaseModel;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 26/05/17.
 */

@Parcel
public class ExampleModel extends BaseModel {

    public List<BaseObject> items = new ArrayList<>();
}
