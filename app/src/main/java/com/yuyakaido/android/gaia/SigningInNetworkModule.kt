package com.yuyakaido.android.gaia

import com.yuyakaido.android.gaia.core.SigningInComponent
import com.yuyakaido.android.gaia.core.domain.app.SigningInScope
import com.yuyakaido.android.gaia.core.domain.entity.Session
import com.yuyakaido.android.gaia.core.domain.repository.SessionRepositoryType
import com.yuyakaido.android.gaia.core.domain.repository.TokenRepositoryType
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPrivate
import com.yuyakaido.android.gaia.core.infrastructure.RetrofitForPublic
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit

@InstallIn(SigningInComponent::class)
@Module
class SigningInNetworkModule : MainNetworkModule() {

  @SigningInScope
  @RetrofitForPublic
  @Provides
  fun provideRetrofitForPublic(): Retrofit {
    return createRetrofitForPublic()
  }

  @SigningInScope
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

}