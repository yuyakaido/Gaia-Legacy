package com.yuyakaido.android.gaia.core

import com.yuyakaido.android.gaia.core.domain.app.ImageLoaderType
import com.yuyakaido.android.gaia.core.domain.entity.Session
import dagger.android.AndroidInjector
import dagger.hilt.EntryPoints
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class ComponentHandler(
  private val appStore: AppStore,
  private val signedOutBuilder: SignedOutComponent.Builder,
  private val signingInBuilder: SigningInComponent.Builder,
  private val signedInBuilder: SignedInComponent.Builder
) {

  private val components = mutableMapOf<String, Any>()

  init {
    GlobalScope.launch {
      appStore.sessionsAsFlow()
        .collect { sessions ->
          sessions.forEach { session ->
            val oldComponent = components[session.id]
            val newComponent = when (session) {
              is SessionState.SignedOut -> {
                if (oldComponent is SignedOutComponent) {
                  oldComponent
                } else {
                  val s = Session.SignedOut(
                    id = session.id
                  )
                  newSignedOutComponent(s)
                }
              }
              is SessionState.SigningIn -> {
                if (oldComponent is SigningInComponent) {
                  oldComponent
                } else {
                  val s = Session.SigningIn(
                    id = session.id
                  )
                  newSigningInComponent(s)
                }
              }
              is SessionState.SignedIn -> {
                if (oldComponent is SignedInComponent) {
                  oldComponent
                } else {
                  val s = Session.SignedIn(
                    id = session.id,
                    token = session.token
                  )
                  newSignedInComponent(s)
                }
              }
            }
            components[session.id] = newComponent
          }
        }
    }
  }

  private fun newSignedOutComponent(session: Session.SignedOut): SignedOutComponent {
    return signedOutBuilder.session(session).build()
  }

  private fun newSigningInComponent(session: Session.SigningIn): SigningInComponent {
    return signingInBuilder.session(session).build()
  }

  private fun newSignedInComponent(session: Session.SignedIn): SignedInComponent {
    return signedInBuilder.session(session).build()
  }

  private fun signedOutInjector(component: Any): SignedOutComponent.Injector {
    return EntryPoints.get(component, SignedOutComponent.Injector::class.java)
  }

  private fun signingInInjector(component: Any): SigningInComponent.Injector {
    return EntryPoints.get(component, SigningInComponent.Injector::class.java)
  }

  private fun signedInInjector(component: Any): SignedInComponent.Injector {
    return EntryPoints.get(component, SignedInComponent.Injector::class.java)
  }

  private fun activeComponent(): Any {
    return components.getValue(activeSession().id)
  }

  private fun activeSignedOutInjector(): SignedOutComponent.Injector {
    return signedOutInjector(activeComponent())
  }

  private fun activeSigningInInjector(): SigningInComponent.Injector {
    return signingInInjector(activeComponent())
  }

  private fun activeSignedInInjector(): SignedInComponent.Injector {
    return signedInInjector(activeComponent())
  }

  fun activeInjector(): AndroidInjector<Any> {
    val state = appStore.stateAsValue()
    return if (state.sessions.isEmpty()) {
      val session = Session.SignedOut()
      signedOutInjector(newSignedOutComponent(session)).androidInjector()
    } else {
      val component = components.getValue(state.session.id)
      when (state.session) {
        is SessionState.SignedOut -> {
          signedOutInjector(component).androidInjector()
        }
        is SessionState.SigningIn -> {
          signingInInjector(component).androidInjector()
        }
        is SessionState.SignedIn -> {
          signedInInjector(component).androidInjector()
        }
      }
    }
  }

  fun activeSession(): Session {
    return appStore.sessionAsValue().toEntity()
  }

  fun activeSignedOutRetrofitForPublic(): Retrofit {
    return activeSignedOutInjector().retrofitForPublic()
  }

  fun activeSigningInRetrofitForPublic(): Retrofit {
    return activeSigningInInjector().retrofitForPrivate()
  }

  fun activeSignedInRetrofitForPublic(): Retrofit {
    return activeSignedInInjector().retrofitForPublic()
  }

  fun activeSigningInRetrofitForPrivate(): Retrofit {
    return activeSigningInInjector().retrofitForPrivate()
  }

  fun activeSignedInRetrofitForPrivate(): Retrofit {
    return activeSignedInInjector().retrofitForPrivate()
  }

  fun activeImageLoader(): ImageLoaderType {
    return signedInInjector(components.getValue(appStore.sessionAsValue().id)).imageLoader()
  }

}