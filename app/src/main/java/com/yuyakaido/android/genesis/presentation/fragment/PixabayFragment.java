package com.yuyakaido.android.genesis.presentation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.yuyakaido.android.genesis.R;
import com.yuyakaido.android.genesis.app.Genesis;
import com.yuyakaido.android.genesis.domain.entity.PixabayMedia;
import com.yuyakaido.android.genesis.domain.usecase.PixabayUseCase;
import com.yuyakaido.android.genesis.presentation.activity.WebViewActivity;
import com.yuyakaido.android.genesis.presentation.adapter.PixabayAdapter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yuyakaido on 3/5/16.
 */
public class PixabayFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @Inject
    PixabayUseCase pixabayUseCase;

    private PixabayAdapter pixabayAdapter;

    public static Fragment newInstance() {
        return new PixabayFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pixabay, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Genesis.getGenesisComponent(this).inject(this);

        pixabayAdapter = new PixabayAdapter(getContext());

        GridView gridView = (GridView) getView().findViewById(R.id.fragment_pixabay_grid_view);
        gridView.setAdapter(pixabayAdapter);
        gridView.setOnItemClickListener(this);

        pixabayUseCase.getPixabayMedias()
                .compose(this.<List<PixabayMedia>>bindToLifecycle())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PixabayMedia>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<PixabayMedia> pixabayMedias) {
                        pixabayAdapter.setPixabayMedias(pixabayMedias);
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PixabayMedia pixabayMedia = pixabayAdapter.getItem(position);
        startActivity(WebViewActivity.createIntent(getContext(), pixabayMedia.pageUrl));
    }

}
