package com.example.apptiendaval2.events

import kotlinx.coroutines.flow.MutableSharedFlow

object ProductEvents {
    // Event to notify that products changed and clients should refresh
    val refresh = MutableSharedFlow<Unit>(replay = 0)
}

