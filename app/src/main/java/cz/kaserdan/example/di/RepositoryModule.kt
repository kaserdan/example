package cz.kaserdan.example.di

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Created by egold
 */
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    internal abstract fun bindAlarmRepository(alarmDatabaseRepository: AlarmDatabaseRepository): AlarmRepository

}