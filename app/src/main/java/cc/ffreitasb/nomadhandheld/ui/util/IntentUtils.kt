package cc.ffreitasb.nomadhandheld.ui.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log

private const val TAG = "IntentUtils"

/**
 * Launches the app identified by [packageName] if installed.
 * Returns false if the package isn't found (graceful no-op).
 */
fun launchApp(context: Context, packageName: String): Boolean {
    return try {
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
            ?: return false
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        true
    } catch (e: Exception) {
        Log.w(TAG, "Could not launch $packageName", e)
        false
    }
}

/**
 * Opens the store page for an app.
 * Tries the Play Store app first; falls back to the [storeUrl] in the browser.
 * If [storeUrl] already points to F-Droid or another source, it opens that directly.
 */
fun openStorePage(context: Context, packageName: String, storeUrl: String) {
    // If the URL is a Play Store URL, try the market:// scheme first
    if (storeUrl.contains("play.google.com")) {
        try {
            val marketIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$packageName")
            ).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
            context.startActivity(marketIntent)
            return
        } catch (e: Exception) {
            // Play Store app not available — fall through to browser
        }
    }

    // Open URL directly (F-Droid, GitHub, or Play Store web fallback)
    try {
        val browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(storeUrl)
        ).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
        context.startActivity(browserIntent)
    } catch (e: Exception) {
        Log.e(TAG, "Could not open store URL: $storeUrl", e)
    }
}
