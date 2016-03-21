package com.yuyakaido.android.genesis.infra.client.converter;

import com.yuyakaido.android.genesis.domain.entity.PixabayMedia;
import com.yuyakaido.android.genesis.infra.client.response.PixabayResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyakaido on 3/5/16.
 */
public class PixabayMediaConverter {

    private PixabayMediaConverter() {}

    public static List<PixabayMedia> convert(PixabayResponse response) {
        if (response == null || response.medias == null) {
            return new ArrayList<>();
        }

        return response.medias;
    }

}
