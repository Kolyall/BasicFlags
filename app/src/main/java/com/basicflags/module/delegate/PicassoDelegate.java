package com.basicflags.module.delegate;


import com.basicflags.module.DependencyInjection;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import dagger.Lazy;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by Nikolay Unuchek on 20.10.2016.
 */
@Accessors(prefix = "m")
public class PicassoDelegate {


    @Inject
    @Getter Lazy<Picasso> mPicassoLazy;

    private PicassoDelegate(){
        DependencyInjection.inject(this);
    }

    public static PicassoDelegate get() {
        return new PicassoDelegate();
    }
}
