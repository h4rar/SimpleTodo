package h4rar.space.simpletodo.database.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import h4rar.space.simpletodo.model.Note
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface NoteRoomDao {

    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes_table nt where nt.tabId = :tabId")
    fun getAllNotesByTabId(tabId: Int): LiveData<List<Note>>

    @Insert
    fun addNote(note: Note)

    @Query(
        "INSERT INTO notes_table(id, title, isCompleted, tabId) " +
                "VALUES (:id, :title, :isCompleted, :tabId)"
    )
    fun addNote(id: UUID, title: String, isCompleted: Boolean, tabId: Int)

    @Query(
        "UPDATE notes_table SET isCompleted = :bool WHERE id = :id"
    )
    fun changeStatus(id: UUID, bool: Int)

    @Query(
        "UPDATE notes_table set title = :title WHERE id = :id"
    )
    suspend fun upd(id: UUID, title: String)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query(
        "DELETE FROM notes_table WHERE tabId = :tabId"
    )
    suspend fun deleteNotesByTabId(tabId: Int)

}