package com.gb.opaltest.core.navigation.api

import androidx.navigation.NavController


interface NavControllerAccessor {

    fun setController(navController: NavController)
    fun clear()
}
