package com.jsmirabal.animeinfo.domain.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DomainCoroutineScope @Inject constructor(): CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    inline operator fun invoke(block: DomainCoroutineScope.() -> Unit) = block(this)
}
