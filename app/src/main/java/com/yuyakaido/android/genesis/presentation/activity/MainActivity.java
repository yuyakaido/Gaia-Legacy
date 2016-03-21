package com.yuyakaido.android.genesis.presentation.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.yuyakaido.android.genesis.R;
import com.yuyakaido.android.genesis.domain.usecase.GithubUseCase;
import com.yuyakaido.android.genesis.domain.usecase.PixabayUseCase;
import com.yuyakaido.android.genesis.presentation.adapter.MainViewPagerAdapter;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    @Inject
    GithubUseCase githubUseCase;

    @Inject
    PixabayUseCase pixabayUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.activity_main_view_pager);
        viewPager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_main_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

}
