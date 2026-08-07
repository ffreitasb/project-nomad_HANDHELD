package cc.ffreitasb.nomadhandheld.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a single curated app in the catalog.
 * Deserialized from assets/curated_apps.json.
 * Schema defined in PRD section 6.
 */
@Serializable
data class AppEntry(
    val id: String,
    val name: String,
    val category: String,
    @SerialName("package_name") val packageName: String,
    @SerialName("store_url") val storeUrl: String,
    @SerialName("description_short") val descriptionShort: String,
    @SerialName("onboarding_md") val onboardingMdPath: String,
    @SerialName("recommended_content") val recommendedContent: List<String> = emptyList(),
    val priority: AppPriority = AppPriority.RECOMMENDED
)

@Serializable
enum class AppPriority {
    @SerialName("critical") CRITICAL,
    @SerialName("recommended") RECOMMENDED,
    @SerialName("optional") OPTIONAL
}
