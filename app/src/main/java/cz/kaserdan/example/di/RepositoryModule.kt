package cz.kaserdan.example.di

import dagger.Binds
import dagger.Module

/**
 * Created by egold
 */
@Module
abstract class RepositoryModule {

    @Binds
    @ApplicationScope
    internal abstract fun bindAlarmRepository(alarmDatabaseRepository: AlarmDatabaseRepository): AlarmRepository

}