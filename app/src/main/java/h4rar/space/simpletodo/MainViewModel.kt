package h4rar.space.simpletodo

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import h4rar.space.simpletodo.database.room.AppRoomDatabase
import h4rar.space.simpletodo.database.room.repository.RoomRepository
import h4rar.space.simpletodo.model.Note
import h4rar.space.simpletodo.model.SimpleObject
import h4rar.space.simpletodo.model.Tab
import h4rar.space.simpletodo.utils.REPOSITORY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.random.Random

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val context = application

    private val _revealedCardIdsList = MutableStateFlow(listOf<UUID>())
    val revealedCardIdsList: StateFlow<List<UUID>> get() = _revealedCardIdsList


    @ExperimentalCoroutinesApi
    @Composable
    fun initDatabase(onSuccess: () -> Unit) {
        val dao = AppRoomDatabase.getInstance(context = context).getRoomDao()
        val note = AppRoomDatabase.getInstance(context = context).getNoteRoomDao()
        REPOSITORY = RoomRepository(dao, note)
        onSuccess()
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.create(note = note)
        }
    }

    fun changeStatus(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.changeStatus(note = note)
        }
    }

    fun addTab(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.create(name)
        }
    }
    fun upTab(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.upTab(id)
        }
    }
    fun downTab(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.downTab(id)
        }
    }

//    fun addBaseTab() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val tab = Tab(name = "Home", notes = listOf())
//            REPOSITORY.create(tab)
//        }
//    }


    fun update(noteId: UUID, title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.update(noteId, title)
        }
    }


    fun updateTab(tabId: Int, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.updateTab(tabId, name)
        }
    }


//    fun addNote(note: Note, tab: Tab) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val note = Note(title = "title12", subtitle = "subTitle12", isCompleted = false) // TODO
//            val filter = tab.notes.stream()
//                .collect(toList())
//            filter.add(note)
//            tab.notes = filter;
//            REPOSITORY.update(tab)
//        }
//    }

//    fun deleteNote(note: Note, tab: Tab) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val filter = tab.notes.stream()
//                .filter { !it.equals(note) }
//                .collect(toList())
//            tab.notes = filter;
//            REPOSITORY.update(tab)
////            REPOSITORY.delete(note = note) {
////                viewModelScope.launch(Dispatchers.Main) {
////                    onSuccess()
////                }
////            }
//        }
//    }


    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.delete(note = note) {
            }
        }
    }

    fun deleteTab(tabId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.deleteTab(tabId = tabId)
        }
    }

    fun readAllNotesByTabId(tabId: Int) = REPOSITORY.readAllNotesByTabId(tabId)

//    fun readAllNotesByTabId(tabId: Int): LiveData<List<Note>> {
//        var readAllNotesByTabId: LiveData<List<Note>> = MutableLiveData<List<Note>>()
//        viewModelScope.launch(Dispatchers.IO) {
//            readAllNotesByTabId = REPOSITORY.readAllNotesByTabId(tabId)
//        }
//        return readAllNotesByTabId;
//    }
//    fun readAllNotesByTabId(tabId: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            REPOSITORY.readAllNotesByTabId(tabId = tabId) {
//            }
//        }
//    }
//    val readAllNotesByTabId: LiveData<List<Note>> by lazy {
//        return@lazy REPOSITORY.readAllNotesByTabId(tabId = 0)
//    }

//    fun readAllNotesByTabId(tabId: Int) : LiveData<List<Note>> {
//        return REPOSITORY.readAllNotesByTabId(tabId = 0)
//    }


//    fun readAllNotesByTabId(tabId: Int) = REPOSITORY.readAllNotesByTabId
//    suspend fun readAllNotesByTabId(tabId: Long): LiveData<List<Note>> {
//        viewModelScope.launch(Dispatchers.IO) {
//        }
//
//        return REPOSITORY.readAllNotesByTabId(tabId = tabId);
//    }

//    fun readAllTabs() = REPOSITORY.readAll
    fun readAllTabsSort() = REPOSITORY.readAllSort

    fun onItemExpanded(cardId: UUID) {
        _revealedCardIdsList.update {
            listOf()
        }
        if (_revealedCardIdsList.value.contains(cardId)) return
        _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
            list.add(cardId)
        }
    }

    fun onItemCollapsed(cardId: UUID) {
        _revealedCardIdsList.update {
            listOf()
        }
        if (!_revealedCardIdsList.value.contains(cardId)) return
        _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
            list.remove(cardId)
        }
    }

//    private val _uiTab = MutableStateFlow<List<Tab>>(listOf())
//    fun readAllTabs1(): StateFlow<List<Tab>> {
//        val readAll = REPOSITORY.readAll.value
//        _uiTab.value = readAll!!
//        return _uiTab.asStateFlow()
//    }


    private val _uiTab = MutableStateFlow<List<Tab>>(listOf())


    private val _uiState = MutableStateFlow<List<Section>>(listOf())
    val uiState = _uiState.asStateFlow()

    private val sections = listOf(
        Section(name = "Section 1"),
        Section(name = "Section 2"),
        Section(name = "Section 3"),
        Section(name = "Section 4"),
        Section(name = "Section 5"),
        Section(name = "Section 6"),
    )

//    fun swapSections(from: Int, to: Int) {
//        val fromItem = _uiState.value[from]
//        val toItem = _uiState.value[to]
//        val newList = _uiState.value.toMutableList()
//        newList[from] = toItem
//        newList[to] = fromItem
//
//        _uiState.value = newList
//    }

//    fun swapSections(uiState: List<Tab>, from: Int, to: Int): List<Tab> {
//        val fromItem = uiState[from]
//        val toItem = uiState[to]
//        val newList = uiState.toMutableList()
//        newList[from] = toItem
//        newList[to] = fromItem
//
//        viewModelScope.launch(Dispatchers.IO) {
//            REPOSITORY.updateOnum(toItem.id, from)
//        }
//        viewModelScope.launch(Dispatchers.IO) {
//            REPOSITORY.updateOnum(fromItem.id, to)
//        }
//        return newList.sortedBy { it.onum }
//    }

    fun sectionClicked(item: Section) {
        println("Clicked $item")
    }


    fun sectionClicked(item: SimpleObject) {
        println("Clicked $item")
    }

    fun sectionClicked(item: Tab) {
        println("Clicked $item")
    }

    fun swapSections(from: Int, to: Int) {
        val fromItem = _uiState.value[from]
        val toItem = _uiState.value[to]
        val newList = _uiState.value.toMutableList()
        newList[from] = toItem
        newList[to] = fromItem

        _uiState.value = newList
    }

//    fun swapSections(from: Int, to: Int) {
//        allRecords.value!![from].onum = to
//        allRecords.value!![to].onum = from
//
//
////        val fromItem = allRecords.value?.get(from)
////        val toItem = allRecords.value?.get(to)
////        val newList = allRecords.value?.toMutableList()
////        if (toItem != null) {
////            newList?.set(from, toItem)
////        }
////        if (fromItem != null) {
////            newList?.set(to, fromItem)
////        }
//
////        allRecords.value = newList
//    }

    init {
        _uiState.value = sections.shuffled()
    }


}

data class Section(
    val id: Int = internalId++,
    val name: String = "",
    val description: String = "",
    val color: Long = Random(id).nextLong()
) {
    companion object {
        private var internalId = 0
    }
}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}