package h4rar.space.simpletodo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import h4rar.space.simpletodo.utils.Constants.Keys.NOTES_TABLE
import java.util.UUID

@Entity(tableName = NOTES_TABLE)
data class Note(

    @PrimaryKey
    val id: UUID = UUID.randomUUID(),

    @ColumnInfo
    var title: String,

    @ColumnInfo
    val isCompleted: Boolean,

    @ColumnInfo
    val tabId: Int = 0,
)