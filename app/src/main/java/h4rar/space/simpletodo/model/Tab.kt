package h4rar.space.simpletodo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import h4rar.space.simpletodo.utils.Constants.Keys.TABS_TABLE

@Entity(tableName = TABS_TABLE)
data class Tab(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo
    val name: String,

    @ColumnInfo
    var onum: Int = 0,

    )