package cz.kaserdan.example.di

import dagger.MapKey
import kotlin.reflect.KClass
import android.arch.lifecycle.ViewModel

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)