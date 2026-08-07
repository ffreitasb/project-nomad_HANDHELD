package cc.ffreitasb.nomadhandheld.data.repository

import cc.ffreitasb.nomadhandheld.data.model.AppEntry
import cc.ffreitasb.nomadhandheld.data.model.AppPriority
import cc.ffreitasb.nomadhandheld.data.model.AppStatus

/**
 * Calculates the overall "kit readiness" progress from a snapshot of app statuses.
 *
 * Algorithm (v1 — documented in PRD section 4.3):
 * All apps with status != NOT_INSTALLED count toward progress, regardless of priority.
 * Weight is equal across all apps. This keeps the UX simple and predictable.
 *
 * Roadmap consideration: future versions could weight CRITICAL apps double,
 * but that adds cognitive load to "Kit X% pronto" without clear benefit in v1.
 */
object ProgressCalculator {

    /**
     * Result of a progress calculation snapshot.
     *
     * @param overallPercent 0–100, rounded integer.
     * @param installedCount Apps with status INSTALLED or READY.
     * @param totalCount Total apps in the catalog.
     * @param criticalReady Count of CRITICAL apps with status READY.
     * @param criticalTotal Total CRITICAL apps in the catalog.
     */
    data class ProgressSnapshot(
        val overallPercent: Int,
        val installedCount: Int,
        val totalCount: Int,
        val criticalReady: Int,
        val criticalTotal: Int
    ) {
        /** True when all CRITICAL apps have been marked READY. */
        val allCriticalReady: Boolean get() = criticalReady >= criticalTotal && criticalTotal > 0

        /** Label for the progress bar, e.g. "Kit 60% pronto". */
        fun progressLabel(): String = "Kit $overallPercent% pronto"
    }

    /**
     * Computes progress given the full catalog and a map of known statuses.
     * Apps not present in [statuses] are treated as NOT_INSTALLED.
     */
    fun calculate(
        apps: List<AppEntry>,
        statuses: Map<String, AppStatus>
    ): ProgressSnapshot {
        if (apps.isEmpty()) return ProgressSnapshot(0, 0, 0, 0, 0)

        val installedCount = apps.count { app ->
            val status = statuses[app.id] ?: AppStatus.NOT_INSTALLED
            status != AppStatus.NOT_INSTALLED
        }

        val criticalApps = apps.filter { it.priority == AppPriority.CRITICAL }
        val criticalReady = criticalApps.count { app ->
            statuses[app.id] == AppStatus.READY
        }

        val overallPercent = (installedCount.toFloat() / apps.size * 100).toInt()

        return ProgressSnapshot(
            overallPercent = overallPercent,
            installedCount = installedCount,
            totalCount = apps.size,
            criticalReady = criticalReady,
            criticalTotal = criticalApps.size
        )
    }
}
