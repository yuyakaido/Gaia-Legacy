package com.yuyakaido.android.genesis.infra.client;

import com.yuyakaido.android.genesis.domain.constant.DomainConst;
import com.yuyakaido.android.genesis.domain.entity.PixabayMedia;
import com.yuyakaido.android.genesis.infra.client.common.CommonClient;
import com.yuyakaido.android.genesis.infra.client.converter.PixabayMediaConverter;
import com.yuyakaido.android.genesis.infra.client.response.PixabayResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by yuyakaido on 3/5/16.
 */
public class PixabayClient {

    private final PixabayService pixabayService;

    @Inject
    public PixabayClient(PixabayService pixabayService) {
        this.pixabayService = pixabayService;
    }

    public Observable<List<PixabayMedia>> getPixabayMedias() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("key", DomainConst.Pixabay.API_KEY);
        return CommonClient.retry(pixabayService.getPixabayMedias(parameters))
                .map(new Func1<PixabayResponse, List<PixabayMedia>>() {
                    @Override
                    public List<PixabayMedia> call(PixabayResponse pixabayResponse) {
                        return PixabayMediaConverter.convert(pixabayResponse);
                    }
                });
    }

    public interface PixabayService {
        @GET("/api")
        Observable<PixabayResponse> getPixabayMedias(@QueryMap Map<String, String> map);
    }

}
