package h4rar.space.simpletodo.database.room.repository

import androidx.lifecycle.LiveData
import h4rar.space.simpletodo.database.DatabaseRepository
import h4rar.space.simpletodo.database.room.dao.NoteRoomDao
import h4rar.space.simpletodo.database.room.dao.TabRoomDao
import h4rar.space.simpletodo.model.Note
import h4rar.space.simpletodo.model.Tab
import kotlinx.coroutines.flow.Flow
import java.util.*

class RoomRepository(
    private val tabRoomDao: TabRoomDao,
    private val noteRoomDao: NoteRoomDao
) : DatabaseRepository {
    override val readAllNotes: Flow<List<Note>>
        get() = noteRoomDao.getAllNotes()

    override val readAll: LiveData<List<Tab>>
        get() = tabRoomDao.getAllTabs()

    override val readAllSort: LiveData<List<Tab>>
        get() = tabRoomDao.readAllSort()

override fun readAllNotesByTabId(tabId: Int) : LiveData<List<Note>> = noteRoomDao.getAllNotesByTabId(tabId)


    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        onSuccess()
    }

    override suspend fun create(note: Note) {
        noteRoomDao.addNote(note.id, note.title, note.isCompleted, note.tabId)
    }
    override suspend fun changeStatus(note: Note) {
        if(note.isCompleted){
            noteRoomDao.changeStatus(note.id, 0)

        } else {
            noteRoomDao.changeStatus(note.id, 1)

        }
    }

    override suspend fun update(id: UUID, title: String) {
        noteRoomDao.upd(id, title)
    }

    override suspend fun updateOnum(id: Int, onum: Int) {
        tabRoomDao.updOnum(id, onum)
    }
    override suspend fun updateTab(id: Int, name: String) {
        tabRoomDao.updateTabNative(id, name)
    }

    override suspend fun upTab(id: Int) {
        tabRoomDao.upTab(id)
    }

    override suspend fun downTab(id: Int) {
        tabRoomDao.downTab(id)
    }

    override suspend fun create(name: String) {
        tabRoomDao.addTab(name)
    }

    override suspend fun update(note: Tab) {
        tabRoomDao.updateTab(tab = note)
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
//        noteRoomDao.updateNote(note = note)
        onSuccess()
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.deleteNote(note = note)
        onSuccess()
    }

    override suspend fun deleteTab(tabId: Int) {
        noteRoomDao.deleteNotesByTabId(tabId = tabId)
        tabRoomDao.deleteTab(tabId = tabId)
    }
}