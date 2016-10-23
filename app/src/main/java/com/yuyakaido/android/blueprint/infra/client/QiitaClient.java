package com.yuyakaido.android.blueprint.infra.client;

import com.yuyakaido.android.blueprint.domain.entity.QiitaItem;

import java.util.List;

import javax.inject.Inject;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yuyakaido on 3/12/16.
 */
public class QiitaClient {

    private QiitaService qiitaService;

    @Inject
    public QiitaClient(QiitaService qiitaService) {
        this.qiitaService = qiitaService;
    }

    public Observable<List<QiitaItem>> getQiitaItems() {
        return qiitaService.getQiitaItems("android");
    }

    public interface QiitaService {
        @GET("/api/v2/items")
        Observable<List<QiitaItem>> getQiitaItems(@Query("query") String query);
    }

}
