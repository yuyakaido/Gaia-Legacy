package com.yuyakaido.android.genesis.infra.client.response;

import com.google.gson.annotations.SerializedName;
import com.yuyakaido.android.genesis.domain.entity.PixabayMedia;

import java.util.List;

/**
 * Created by yuyakaido on 3/5/16.
 */
public class PixabayResponse {

    @SerializedName("hits")
    public List<PixabayMedia> medias;

}
