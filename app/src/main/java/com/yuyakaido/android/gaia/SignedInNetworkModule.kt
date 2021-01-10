package com.yuyakaido.android.gaia

import android.app.Application
import com.yuyakaido.android.gaia.core.SignedInComponent
import com.yuyakaido.android.gaia.core.domain.app.ImageLoaderType
import com.yuyakaido.android.gaia.core.domain.app.SignedInScope
import com.yuyakaido.android.gaia.core.domain.entity.Session
import com.yuyakaido.android.gaia.core.domain.repository.SessionRepositoryType
import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.ImageLoader
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPrivate
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPublic
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit

@InstallIn(SignedInComponent::class)
@Module
class SignedInNetworkModule : MainNetworkModule() {

  @SignedInScope
  @RetrofitForPublic
  @Provides
  fun provideRetrofitForPublic(): Retrofit {
    return createRetrofitForPublic()
  }

  @SignedInScope
  @RetrofitForPrivate
  @Provides
  fun provideRetrofitForPrivate(
    session: Session,
    sessionRepository: SessionRepositoryType,
    tokenRepository: TokenRepositoryType
  ): Retrofit {
    return createRetrofitForPrivate(
      session = session,
      sessionRepository = sessionRepository,
      tokenRepository = tokenRepository
    )
  }

  @SignedInScope
  @Provides
  fun provideImageLoaderType(
    application: Application
  ): ImageLoaderType {
    return ImageLoader(
      application = application
    )
  }

}