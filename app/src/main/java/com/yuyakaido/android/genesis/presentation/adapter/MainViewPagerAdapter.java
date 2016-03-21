package com.yuyakaido.android.genesis.presentation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yuyakaido.android.genesis.presentation.fragment.GithubFragment;
import com.yuyakaido.android.genesis.presentation.fragment.PixabayFragment;
import com.yuyakaido.android.genesis.presentation.fragment.QiitaFragment;

/**
 * Created by yuyakaido on 3/5/16.
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    public enum MainTab {
        Github, Pixabay, Qiita;

        public static MainTab valueOf(int i) {
            for (MainTab mainTab : values()) {
                if (mainTab.ordinal() == i) {
                    return mainTab;
                }
            }
            return null;
        }
    }

    public MainViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return MainTab.values().length;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (MainTab.valueOf(position)) {
            case Github:
                fragment = GithubFragment.newInstance();
                break;
            case Pixabay:
                fragment = PixabayFragment.newInstance();
                break;
            case Qiita:
                fragment = QiitaFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return MainTab.valueOf(position).name();
    }

}
