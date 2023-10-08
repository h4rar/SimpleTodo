package h4rar.space.simpletodo.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.accompanist.pager.*
import h4rar.space.simpletodo.MainViewModel
import h4rar.space.simpletodo.model.Note
import h4rar.space.simpletodo.model.SimpleObject
import h4rar.space.simpletodo.model.Tab
import h4rar.space.simpletodo.utils.Constants
import h4rar.space.simpletodo.utils.Constants.Keys.ADD_NEW_TASK
import h4rar.space.simpletodo.utils.Constants.Keys.EMPTY
import h4rar.space.simpletodo.utils.dp
import kotlinx.coroutines.*
import java.util.*
import java.util.stream.Collectors.toList

const val ACTION_ITEM_SIZE = 46
const val CARD_HEIGHT = 46
const val CARD_OFFSET = -112f

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(
    ExperimentalPagerApi::class
)
@ExperimentalCoroutinesApi
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val tads = viewModel.readAllTabsSort().observeAsState(listOf()).value
    val tabFromDb = tads.stream().map { SimpleObject(it.id, it.name) }.collect(toList())
    val pagerState = rememberPagerState(initialPage = 0)
    val tabs = if (tabFromDb.isEmpty()) return else tabFromDb

    var tab: Tab? = null

    Column {
        Scaffold(
            modifier = Modifier
                .fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.background
        ) {
            Column() {
                Tabs(
                    tabs = tabs,
                    pagerState = pagerState,
                    navController = navController
                )
                HorizontalPager(
                    state = pagerState,
                    count = tabs.size,
                    userScrollEnabled = true,
                    modifier = Modifier
                        .fillMaxWidth()
                ) { page ->
                    Column {
                        Scaffold(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.93f),
                            backgroundColor = MaterialTheme.colors.background
                        ) { paddingValues ->
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues)
                            ) {
                                Box() {
                                    TabsContent(
                                        tabs = tads,
                                        paddingValues = paddingValues,
                                        viewModel = viewModel,
                                        page = page
                                    )
                                }
                            }
                        }
                    }
                }
                Dialog(
                    title = Constants.Keys.TASK,
                    confirmButtonTitle = Constants.Keys.OK,
                    dialogState = createDialogState,
                    sourceText = null,
                    onConfirmButtonClick = {
                        val note = Note(UUID.randomUUID(), it, false, tab!!.id)
                        viewModel.addNote(note)
                    }
                )
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .alpha(1F),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    TextButton(
                        onClick = {
                            val currentPage = pagerState.currentPage
                            tab = tads[currentPage]
                            createDialogState.value = true
                        }) {
                        Text(
                            text = ADD_NEW_TASK,
                            style = textStyle()
                        )
                    }
                }
            }
        }
    }
    Dialog(
        title = Constants.Keys.TASK,
        confirmButtonTitle = Constants.Keys.OK,
        dialogState = createDialogState,
        sourceText = null,
        onConfirmButtonClick = {
            val note = Note(UUID.randomUUID(), it, false, tab!!.id)
            viewModel.addNote(note)
        }
    )
}

val createDialogState by lazy { mutableStateOf(false) }
val editDialogState by lazy { mutableStateOf(false) }

@OptIn(
    ExperimentalLifecycleComposeApi::class,
)
@Composable
fun TabsContent(
    tabs: List<Tab>,
    paddingValues: PaddingValues,
    viewModel: MainViewModel,
    page: Int
) {
    val coroutineScopeUpdate = rememberCoroutineScope()
    val tab = tabs[page]
    val notes = viewModel.readAllNotesByTabId(tabId = tab.id).observeAsState(listOf()).value
    val revealedCardIds by viewModel.revealedCardIdsList.collectAsStateWithLifecycle()
    var title by remember { mutableStateOf(EMPTY) }
    var n: Note? = null

    LazyColumn(Modifier.padding(paddingValues)) {
        items(notes) { note ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        .fillMaxSize(100f),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    ActionsRow(
                        actionIconSize = ACTION_ITEM_SIZE.dp,
                        onDelete = {
                            viewModel.deleteNote(note)
                            viewModel.onItemCollapsed(note.id)
                        },
                        onEdit = {
                            title = note.title
                            n = note
                            coroutineScopeUpdate.launch { editDialogState.value = true }
                        },
                    )
                }
                DraggableCard(
                    note = note,
                    isRevealed = revealedCardIds.contains(note.id),
                    cardOffset = CARD_OFFSET.dp(),
                    onExpand = { viewModel.onItemExpanded(note.id) },
                    onCollapse = { viewModel.onItemCollapsed(note.id) },
                    onClick = {
                        viewModel.changeStatus(note)
                    }
                )
            }
            Dialog(
                title = Constants.Keys.UPDATE_NOTE,
                confirmButtonTitle = Constants.Keys.OK,
                dialogState = editDialogState,
                sourceText = title,
                onConfirmButtonClick = {
                    viewModel.update(noteId = n!!.id, it)
                }
            )
        }
    }
}

@Composable
fun textStyle() = TextStyle(
    fontSize = 25.sp,
    fontFamily = FontFamily.SansSerif,
    color = MaterialTheme.colors.secondary,
    textAlign = TextAlign.Center,
    fontWeight = FontWeight(400)
)

@Composable
private fun CustomIndicator() {
}

@OptIn(
    ExperimentalPagerApi::class, ExperimentalMaterialApi::class
)
@Composable
fun Tabs(
    tabs: List<SimpleObject>,
    pagerState: PagerState,
    navController: NavController,
) {
    val scope = rememberCoroutineScope()
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = {
                navController.navigate(route = Screen.Settings.route)
            },
            content = {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    tint = Color.Gray,
                    contentDescription = "edit action",
                )
            },
        )
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.secondary,
            edgePadding = 10.dp,
            divider = @Composable {
                Divider(
                    thickness = 0.dp,
                    color = MaterialTheme.colors.background,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 0.dp)
                )
            },
            indicator = { CustomIndicator() }
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    text = {
                        Text(
                            text = tab.name,
                            style = TextStyle(
                                fontSize = if (pagerState.currentPage == index) 32.sp else 16.sp,
                                fontFamily = FontFamily.SansSerif,
                                color = if (pagerState.currentPage == index) MaterialTheme.colors.secondary else Color.Gray,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight(400)
                            )
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.scrollToPage(
                                page = index
                            )
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun Dialog(
    title: String,
    sourceText: String?,
    confirmButtonTitle: String,
    dialogState: MutableState<Boolean>,
    onConfirmButtonClick: (String) -> Unit
) {
    if (!dialogState.value) {
        return
    }
    val text = remember {
        if (sourceText == null) mutableStateOf("") else mutableStateOf(sourceText)
    }
    AlertDialog(onDismissRequest = {
        dialogState.value = false
    },
        confirmButton = {
            TextButton(onClick = {
                onConfirmButtonClick(text.value)
                dialogState.value = false
            }) {
                Text(text = confirmButtonTitle, style = buttonTextStyle())
            }
        },
        dismissButton = {
            TextButton(onClick = {
                dialogState.value = false
            }) {
                Text(
                    text = "Cancel",
                    style = buttonTextStyle()
                )
            }
        },
        title = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = title, style = buttonTextStyle())
                TextField(
                    value = text.value,
                    colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colors.secondary),
                    onValueChange = {
                        text.value = it
                    })
            }
        }
    )
}

@Composable
fun AlertDialog(
    title: String,
    confirmButtonTitle: String,
    dialogState: MutableState<Boolean>,
    onConfirmButtonClick: () -> Unit
) {
    if (!dialogState.value) {
        return
    }

    AlertDialog(onDismissRequest = {
        dialogState.value = false
    },
        confirmButton = {
            TextButton(onClick = {
                onConfirmButtonClick()
                dialogState.value = false
            }) {
                Text(
                    text = confirmButtonTitle,
                    style = buttonTextStyle()
                )
            }
        },
        dismissButton = {
            TextButton(onClick = {
                dialogState.value = false
            }) {
                Text(
                    text = "Cancel",
                    style = buttonTextStyle()
                )
            }
        },
        title = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = title)
            }
        }
    )
}

@Composable
private fun buttonTextStyle() = TextStyle(
    fontFamily = FontFamily.Default,
    color = MaterialTheme.colors.secondary,
)
