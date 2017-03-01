package com.basicflags.module.modules;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.StatFs;

import com.jakewharton.picasso.OkHttp3Downloader;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by Nikolay Unuchek on 08.09.2016.
 */
@Module
public class OkHttpDownloaderModule {
    private static final String TAG = OkHttpDownloaderModule.class.getSimpleName();
    public static final String KEY_PICASSO_OKHTTPDOWNLOADER = "PICASSO_OKHTTPDOWNLOADER";

    private static final String BIG_CACHE_PATH = "picasso-big-cache";
    private static final int MIN_DISK_CACHE_SIZE = 16 * 1024 * 1024; // 16MB
    private static final int MAX_DISK_CACHE_SIZE = 512 * 1024 * 1024; // 512MB

    private static final float MAX_AVAILABLE_SPACE_USE_FRACTION = 0.9f;
    private static final float MAX_TOTAL_SPACE_USE_FRACTION = 0.25f;
    
    @Provides @Named(KEY_PICASSO_OKHTTPDOWNLOADER)
    @Singleton
    OkHttp3Downloader providesPicassoOkHttpClient(Context context) {
        File cacheDir = createDefaultCacheDir(context, BIG_CACHE_PATH);
        long cacheSize = calculateDiskCacheSize(cacheDir);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(cacheDir, cacheSize))
                .build();
        return new OkHttp3Downloader(okHttpClient);
    }

    static File createDefaultCacheDir(Context context, String path) {
        File cacheDir = context.getApplicationContext().getExternalCacheDir();
        if (cacheDir == null)
            cacheDir = context.getApplicationContext().getCacheDir();
        File cache = new File(cacheDir, path);
        if (!cache.exists()) {
            cache.mkdirs();
        }
        return cache;
    }


    static long calculateDiskCacheSize(File dir) {
        long size = Math.min(calculateAvailableCacheSize(dir), MAX_DISK_CACHE_SIZE);
        return Math.max(size, MIN_DISK_CACHE_SIZE);
    }

    @SuppressLint("NewApi")
    static long calculateAvailableCacheSize(File dir) {
        long size = 0;
        try {
            StatFs statFs = new StatFs(dir.getAbsolutePath());
            int sdkInt = Build.VERSION.SDK_INT;
            long totalBytes;
            long availableBytes;
            if (sdkInt < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                int blockSize = statFs.getBlockSize();
                availableBytes = ((long) statFs.getAvailableBlocks()) * blockSize;
                totalBytes = ((long) statFs.getBlockCount()) * blockSize;
            } else {
                availableBytes = statFs.getAvailableBytes();
                totalBytes = statFs.getTotalBytes();
            }
            // Target at least 90% of available or 25% of total space
            size = (long) Math.min(availableBytes * MAX_AVAILABLE_SPACE_USE_FRACTION, totalBytes
                    * MAX_TOTAL_SPACE_USE_FRACTION);
        } catch (IllegalArgumentException ignored) {
            // ignored
        }
        return size;
    }

}