package uz.lazydevv.zbekztask.presentation.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.lazydevv.zbekztask.data.preferences.AppSharedPrefs
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    private const val SHARED_PREF_NAME = "app_shared_prefs"

    @Provides
    @Singleton
    fun provideAppSharedPrefs(
        @ApplicationContext context: Context
    ): AppSharedPrefs {
        return AppSharedPrefs(context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE))
    }
}