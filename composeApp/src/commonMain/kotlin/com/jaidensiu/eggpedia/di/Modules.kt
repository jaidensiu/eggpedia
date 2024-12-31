package com.jaidensiu.eggpedia.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.jaidensiu.eggpedia.data.EggsRepository
import com.jaidensiu.eggpedia.data.IEggsRepository
import com.jaidensiu.eggpedia.data.local.DatabaseInstance
import com.jaidensiu.eggpedia.data.local.LocalEggsDatabase
import com.jaidensiu.eggpedia.data.remote.HttpClientInstance
import com.jaidensiu.eggpedia.data.remote.IRemoteEggsApi
import com.jaidensiu.eggpedia.data.remote.RemoteEggsApi
import com.jaidensiu.eggpedia.ui.details.EggDetailsViewModel
import com.jaidensiu.eggpedia.ui.games.EggQuizGamesViewModel
import com.jaidensiu.eggpedia.ui.games.image.ImageMatchingGameViewModel
import com.jaidensiu.eggpedia.ui.home.HomeViewModel
import com.jaidensiu.eggpedia.ui.list.EggsListViewModel
import com.jaidensiu.eggpedia.ui.shared.SelectedEggViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientInstance.create(get()) }
    single { get<DatabaseInstance>().create().setDriver(BundledSQLiteDriver()).build() }
    single { get<LocalEggsDatabase>().localEggDao }
    singleOf(::RemoteEggsApi).bind<IRemoteEggsApi>()
    singleOf(::EggsRepository).bind<IEggsRepository>()
    viewModelOf(::EggsListViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::EggDetailsViewModel)
    viewModelOf(::SelectedEggViewModel)
    viewModelOf(::EggQuizGamesViewModel)
    viewModelOf(::ImageMatchingGameViewModel)
}
