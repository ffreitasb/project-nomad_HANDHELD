package cc.ffreitasb.nomadhandheld

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

/**
 * Application class that holds the singleton DataStore instance.
 * Using preferencesDataStore delegate guarantees a single instance
 * across the entire app lifetime.
 */
class NomadApplication : Application() {
    // Accessed via NomadApplication.instance.progressDataStore
    companion object {
        lateinit var instance: NomadApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}

/**
 * Top-level DataStore extension on Context.
 * preferencesDataStore guarantees a single DataStore per name per process.
 */
val Application.progressDataStore: DataStore<Preferences>
        by preferencesDataStore(name = "nomad_progress")
