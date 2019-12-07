package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.profile.ProfileFragment
import com.yuyakaido.android.gaia.profile.ProfileModule
import com.yuyakaido.android.gaia.subreddit.list.SubredditListFragment
import com.yuyakaido.android.gaia.subreddit.list.SubredditListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

  @ContributesAndroidInjector(modules = [SubredditListModule::class])
  abstract fun contributeSubredditListFragment(): SubredditListFragment

  @ContributesAndroidInjector(modules = [ProfileModule::class])
  abstract fun contributeProfileFragment(): ProfileFragment

}