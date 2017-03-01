package com.basicflags.service;

import com.basicflags.models.Flag;
import com.basicflags.module.service.APIService;

import java.util.List;

import rx.Observable;


/**
 * Created by Nick Unuchek (skype: kolyall) on 30.09.2016.
 */

public class RxApiService {
    private static final String TAG = RxApiService.class.getSimpleName();

    APIService mAPIService;

    public RxApiService(APIService database){
        this.mAPIService = database;
    }

    public Observable<List<Flag>> getFlags() {
        return mAPIService.getFlagsList();
    }
}
