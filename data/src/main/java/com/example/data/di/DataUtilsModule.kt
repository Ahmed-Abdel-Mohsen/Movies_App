package com.example.data.di

import com.example.base.utils.NetworkConnectivityHelper
import org.koin.dsl.module


/**
 * Koin utils module.
 */
val dataUtilsModule = module {

    single { NetworkConnectivityHelper() }

}