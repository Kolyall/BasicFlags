package com.basicflags.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Nick Unuchek (skype: kolyall) on 01.03.2017.
 */
@Accessors(prefix = "m")
@NoArgsConstructor
public class Flag {
    @SerializedName("name")
    @Getter String mName;
    @SerializedName("flag_128")
    @Getter String mFlagLogo;
}
