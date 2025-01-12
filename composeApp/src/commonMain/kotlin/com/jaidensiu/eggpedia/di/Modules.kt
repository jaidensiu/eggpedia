package com.jaidensiu.eggpedia.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.jaidensiu.eggpedia.data.local.DatabaseFactory
import com.jaidensiu.eggpedia.data.local.LocalDatabase
import com.jaidensiu.eggpedia.data.remote.HttpClientFactory
import com.jaidensiu.eggpedia.data.remote.eggs.IRemoteEggsApi
import com.jaidensiu.eggpedia.data.remote.eggs.RemoteEggsApi
import com.jaidensiu.eggpedia.data.repositories.eggs.EggsRepository
import com.jaidensiu.eggpedia.data.repositories.eggs.IEggsRepository
import com.jaidensiu.eggpedia.data.repositories.minigames.IMinigamesRepository
import com.jaidensiu.eggpedia.data.repositories.minigames.MinigamesRepository
import com.jaidensiu.eggpedia.ui.details.EggDetailsViewModel
import com.jaidensiu.eggpedia.ui.home.HomeViewModel
import com.jaidensiu.eggpedia.ui.list.EggsListViewModel
import com.jaidensiu.eggpedia.ui.minigames.MinigamesViewModel
import com.jaidensiu.eggpedia.ui.minigames.memory.MemoryMatchingMinigameViewModel
import com.jaidensiu.eggpedia.ui.minigames.recipe.RecipeMatchingMinigameViewModel
import com.jaidensiu.eggpedia.ui.shared.SelectedEggViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    single { get<DatabaseFactory>().create().setDriver(BundledSQLiteDriver()).build() }
    single { get<LocalDatabase>().eggsDao }
    single { get<LocalDatabase>().minigamesDao }
    singleOf(::RemoteEggsApi).bind<IRemoteEggsApi>()
    singleOf(::EggsRepository).bind<IEggsRepository>()
    singleOf(::MinigamesRepository).bind<IMinigamesRepository>()
    viewModelOf(::EggsListViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::EggDetailsViewModel)
    viewModelOf(::SelectedEggViewModel)
    viewModelOf(::MinigamesViewModel)
    viewModelOf(::RecipeMatchingMinigameViewModel)
    viewModelOf(::MemoryMatchingMinigameViewModel)
}
