package com.yuyakaido.android.genesis.presentation.fragment;

import com.trello.rxlifecycle.components.support.RxFragment;
import com.yuyakaido.android.genesis.app.Genesis;

/**
 * Created by yuyakaido on 3/5/16.
 */
public class BaseFragment extends RxFragment {

    @Override
    public void onDestroy() {
        super.onDestroy();
        Genesis.getRefWatcher(getContext()).watch(this);
    }

}
