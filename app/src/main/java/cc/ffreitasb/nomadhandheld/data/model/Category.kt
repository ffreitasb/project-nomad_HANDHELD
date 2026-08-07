package cc.ffreitasb.nomadhandheld.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents one of the 5 curated categories.
 * Defined statically in code (not driven by JSON) since categories are
 * fixed in v1 per PRD section 4.1.
 */
@Serializable
data class Category(
    val id: String,
    @SerialName("display_name") val displayName: String,
    /** Material icon name (use androidx.compose.material.icons.Icons.*) */
    val icon: String
)

/**
 * The 5 fixed categories — defined in PRD section 4.1.
 * Any change here requires product approval (not just a code change).
 */
object Categories {
    val INFO_LIBRARY = Category(
        id = "info-library",
        displayName = "Biblioteca de Informação",
        icon = "MenuBook"
    )
    val LOCAL_AI = Category(
        id = "local-ai",
        displayName = "IA Local",
        icon = "Psychology"
    )
    val OFFLINE_MAPS = Category(
        id = "offline-maps",
        displayName = "Mapas Offline",
        icon = "Map"
    )
    val EDUCATION = Category(
        id = "education",
        displayName = "Educação",
        icon = "School"
    )
    val DATA_TOOLS = Category(
        id = "data-tools",
        displayName = "Ferramentas de Dados",
        icon = "Build"
    )

    val all = listOf(INFO_LIBRARY, LOCAL_AI, OFFLINE_MAPS, EDUCATION, DATA_TOOLS)

    fun findById(id: String): Category? = all.find { it.id == id }
}
