package com.yuyakaido.android.blueprint.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yuyakaido.android.blueprint.R;
import com.yuyakaido.android.blueprint.app.App;
import com.yuyakaido.android.blueprint.domain.entity.QiitaItem;
import com.yuyakaido.android.blueprint.domain.usecase.QiitaUseCase;
import com.yuyakaido.android.blueprint.presentation.activity.WebViewActivity;
import com.yuyakaido.android.blueprint.presentation.adapter.QiitaAdapter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yuyakaido on 3/12/16.
 */
public class QiitaFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @Inject
    QiitaUseCase qiitaUseCase;

    private QiitaAdapter qiitaAdapter;

    public static Fragment newInstance() {
        return new QiitaFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qiita, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        App.getAppComponent(this).inject(this);

        qiitaAdapter = new QiitaAdapter(getContext());

        ListView listView = (ListView) getView().findViewById(R.id.fragment_qiita_list_view);
        listView.setAdapter(qiitaAdapter);
        listView.setOnItemClickListener(this);

        qiitaUseCase.getQiitaItems()
                .compose(this.<List<QiitaItem>>bindToLifecycle())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<QiitaItem>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<QiitaItem> qiitaItems) {
                        qiitaAdapter.setQiitaItems(qiitaItems);
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        QiitaItem qiitaItem = qiitaAdapter.getItem(position);
        startActivity(WebViewActivity.createIntent(getContext(), qiitaItem.url));
    }

}
