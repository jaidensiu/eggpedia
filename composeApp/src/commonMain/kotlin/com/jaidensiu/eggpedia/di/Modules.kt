package com.jaidensiu.eggpedia.di

import com.jaidensiu.eggpedia.data.EggsRepository
import com.jaidensiu.eggpedia.data.HttpClientInstance
import com.jaidensiu.eggpedia.data.IEggsRepository
import com.jaidensiu.eggpedia.data.IRemoteEggsApi
import com.jaidensiu.eggpedia.data.RemoteEggsApi
import com.jaidensiu.eggpedia.presentation.EggsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientInstance.create(get()) }
    singleOf(::RemoteEggsApi).bind<IRemoteEggsApi>()
    singleOf(::EggsRepository).bind<IEggsRepository>()
    viewModelOf(::EggsViewModel)
}
