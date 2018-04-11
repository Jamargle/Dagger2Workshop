package com.mobileasone.dagger2workshop

import android.app.Activity
import android.app.Application
import com.mobileasone.dagger2workshop.di.DaggerSampleAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * This is the entry point of our application and is where the dependency injection
 * setup starts.
 */
class Dagger2WorkshopApp : Application(),
        HasActivityInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerSampleAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

}
