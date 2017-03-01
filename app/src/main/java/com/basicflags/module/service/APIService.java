package com.basicflags.module.service;


import com.basicflags.models.Flag;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Nick Unuchek (skype: kolyall) on 04.10.2016.
 */

public interface APIService {
    public static final String BASE_URL = "https://cristiroma.github.io/";

    @GET("/countries/data/json/countries.json")
    Observable<List<Flag>> getFlagsList();
}
