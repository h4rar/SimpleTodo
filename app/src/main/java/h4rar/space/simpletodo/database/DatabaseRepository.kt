package h4rar.space.simpletodo.database

import androidx.lifecycle.LiveData
import h4rar.space.simpletodo.model.Note
import h4rar.space.simpletodo.model.Tab
import kotlinx.coroutines.flow.Flow
import java.util.*

interface DatabaseRepository {
    val readAllNotes: Flow<List<Note>>
    val readAll: LiveData<List<Tab>>
    val readAllSort: LiveData<List<Tab>>

    fun readAllNotesByTabId(tabId: Int): LiveData<List<Note>>
    suspend fun create(note: Note, onSuccess: () -> Unit)
    suspend fun create(note: Note)
    suspend fun changeStatus(note: Note)
    suspend fun create(name: String)
    suspend fun update(tab: Tab)
    suspend fun update(id: UUID, title: String)

    suspend fun updateTab(id: Int, name: String)
    suspend fun updateOnum(id: Int, onum: Int)

    suspend fun upTab(id: Int)
    suspend fun downTab(id: Int)
    suspend fun update(note: Note, onSuccess: () -> Unit)
    suspend fun delete(note: Note, onSuccess: () -> Unit)
    suspend fun deleteTab(tabId: Int)
}