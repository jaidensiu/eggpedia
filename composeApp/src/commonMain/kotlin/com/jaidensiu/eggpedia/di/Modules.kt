package com.jaidensiu.eggpedia.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.jaidensiu.eggpedia.data.local.DatabaseInstance
import com.jaidensiu.eggpedia.data.local.LocalDatabase
import com.jaidensiu.eggpedia.data.remote.HttpClientInstance
import com.jaidensiu.eggpedia.data.remote.egg.IRemoteEggsApi
import com.jaidensiu.eggpedia.data.remote.egg.RemoteEggsApi
import com.jaidensiu.eggpedia.data.repositories.egg.EggsRepository
import com.jaidensiu.eggpedia.data.repositories.egg.IEggsRepository
import com.jaidensiu.eggpedia.data.repositories.minigame.IMinigamesRepository
import com.jaidensiu.eggpedia.data.repositories.minigame.MinigamesRepository
import com.jaidensiu.eggpedia.ui.details.EggDetailsViewModel
import com.jaidensiu.eggpedia.ui.home.HomeViewModel
import com.jaidensiu.eggpedia.ui.list.EggsListViewModel
import com.jaidensiu.eggpedia.ui.minigames.MinigamesViewModel
import com.jaidensiu.eggpedia.ui.minigames.memory.MemoryMatchingMinigameViewModel
import com.jaidensiu.eggpedia.ui.minigames.speed.SpeedMatchingMinigameViewModel
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
    single { get<LocalDatabase>().eggDao }
    single { get<LocalDatabase>().minigameDao }
    singleOf(::RemoteEggsApi).bind<IRemoteEggsApi>()
    singleOf(::EggsRepository).bind<IEggsRepository>()
    singleOf(::MinigamesRepository).bind<IMinigamesRepository>()
    viewModelOf(::EggsListViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::EggDetailsViewModel)
    viewModelOf(::SelectedEggViewModel)
    viewModelOf(::MinigamesViewModel)
    viewModelOf(::SpeedMatchingMinigameViewModel)
    viewModelOf(::MemoryMatchingMinigameViewModel)
}
