package com.yuyakaido.android.blueprint.infra.client.response;

import com.google.gson.annotations.SerializedName;
import com.yuyakaido.android.blueprint.domain.entity.PixabayMedia;

import java.util.List;

/**
 * Created by yuyakaido on 3/5/16.
 */
public class PixabayResponse {

    @SerializedName("hits")
    public List<PixabayMedia> medias;

}
