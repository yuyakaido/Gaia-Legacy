package com.yuyakaido.android.gaia.core.java

import java.util.concurrent.atomic.AtomicLong

data class Session(
    val id: Long = counter.getAndIncrement(),
    val environment: Environment
) {

    companion object {
        private val counter = AtomicLong()
    }

    override fun toString(): String {
        return "$id: ${environment.title}"
    }

}