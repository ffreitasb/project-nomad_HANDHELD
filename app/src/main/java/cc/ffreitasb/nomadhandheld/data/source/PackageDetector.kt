package cc.ffreitasb.nomadhandheld.data.source

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log

private const val TAG = "PackageDetector"

/**
 * Detects whether a package is installed on the device via PackageManager.
 *
 * This is the core feature that differentiates NOMAD:HANDHELD from a static list.
 * See PRD section 5 — the reason Kotlin native was chosen over PWA.
 *
 * Note: requires the target package to be declared in AndroidManifest.xml
 * under <queries> (API 30+). Packages not declared there are invisible.
 */
object PackageDetector {

    /**
     * Returns true if the given [packageName] is installed on this device.
     *
     * Handles the API 33+ flag change (GET_META_DATA vs MATCH_UNINSTALLED_PACKAGES).
     * We only check for installed apps — not disabled/uninstalled stubs.
     */
    fun isInstalled(context: Context, packageName: String): Boolean {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.packageManager.getPackageInfo(
                    packageName,
                    PackageManager.PackageInfoFlags.of(0)
                )
            } else {
                @Suppress("DEPRECATION")
                context.packageManager.getPackageInfo(packageName, 0)
            }
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        } catch (e: Exception) {
            Log.w(TAG, "Unexpected error checking package '$packageName'", e)
            false
        }
    }

    /**
     * Returns a map of packageName → isInstalled for a batch of packages.
     * More efficient than calling isInstalled() in a loop when you need many at once.
     */
    fun checkAll(context: Context, packageNames: List<String>): Map<String, Boolean> =
        packageNames.associateWith { isInstalled(context, it) }
}
