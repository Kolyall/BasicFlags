package com.basicflags.module.modules;

import android.content.Context;
import android.net.Uri;

import com.basicflags.module.annotations.ForApplication;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executors;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Nikolay Unuchek on 08.09.2016.
 */
@Module
public class PicassoModule {
    private static final String TAG = PicassoModule.class.getSimpleName();

    @Provides
    @Singleton
    Picasso providesPicasso(@ForApplication Context context, @Named(OkHttpDownloaderModule.KEY_PICASSO_OKHTTPDOWNLOADER)
            OkHttp3Downloader okHttpDownloader) {
        return new Picasso.Builder(context).listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        }).downloader(okHttpDownloader)
          .executor(Executors.newSingleThreadExecutor())//avoid OutOfMemoryError
          .build();
    }
}