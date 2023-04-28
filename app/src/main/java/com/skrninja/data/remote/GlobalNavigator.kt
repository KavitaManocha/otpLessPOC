package com.skrninja.data.remote

object GlobalNavigator {

    private var handler: GlobalNavigationHandler? = null

    fun registerHandler(handler: GlobalNavigationHandler) {
        GlobalNavigator.handler = handler
    }

    fun unregisterHandler() {
        handler = null
    }

    fun logout() {
        handler?.logout()
    }

    fun rejected() {
        handler?.rejected()
    }
}

interface GlobalNavigationHandler {
    fun logout()
    fun rejected()
}