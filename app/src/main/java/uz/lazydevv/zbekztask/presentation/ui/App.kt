package uz.lazydevv.zbekztask.presentation.ui

import android.app.Application
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import dagger.hilt.android.HiltAndroidApp
import java.io.File

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val leastRecentlyUsedCacheEvictor = LeastRecentlyUsedCacheEvictor(90 * 1024 * 1024)
        val databaseProvider: DatabaseProvider = StandaloneDatabaseProvider(this)
        exoPlayerCache = SimpleCache(File(cacheDir, "cache"), leastRecentlyUsedCacheEvictor, databaseProvider)
    }

    companion object {

        lateinit var exoPlayerCache: SimpleCache
    }
}