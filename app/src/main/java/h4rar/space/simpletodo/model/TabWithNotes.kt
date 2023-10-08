package h4rar.space.simpletodo.model

import androidx.room.Embedded
import androidx.room.Relation

data class TabWithNotes(
    @Embedded val classRoom: Tab,
    @Relation(
        parentColumn = "id",
        entityColumn = "tab_id"
    )
    val students: List<Note>

)