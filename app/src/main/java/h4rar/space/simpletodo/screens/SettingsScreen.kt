package h4rar.space.simpletodo.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import h4rar.space.simpletodo.MainViewModel
import h4rar.space.simpletodo.R
import h4rar.space.simpletodo.model.Tab
import h4rar.space.simpletodo.utils.Constants
import h4rar.space.simpletodo.utils.Constants.Keys.ADD_NEW_TAB


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val createDialogState by lazy { mutableStateOf(false) }

    Scaffold(backgroundColor = MaterialTheme.colors.background) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                content = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        tint = Color.Gray,
                        contentDescription = "edit action",
                    )
                },
            )

            Column(modifier = Modifier.fillMaxHeight(0.9f)) {
                val tabs = viewModel.readAllTabsSort().observeAsState(mutableListOf())
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(space = 24.dp),
                    contentPadding = PaddingValues(all = 22.dp)
                ) {
                    item {
                        Text(
                            text = "Tabs list:",
                            style = textStyle()
                        )
                        Spacer(modifier = Modifier.height(height = 8.dp))
                    }

                    val values = tabs.value
                    itemsIndexed(values) { index, item ->
                        ItemLayout(index = index, viewModel = viewModel, values = values)
                    }
                }
            }

            Column {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 37.dp)
                        .alpha(1F),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    TextButton(
                        onClick = {
                            createDialogState.value = true
                        }) {
                        Text(
                            text = ADD_NEW_TAB,
                            style = textStyle()
                        )
                    }
                }
            }
            Dialog(
                title = Constants.Keys.CREATE_TAB,
                confirmButtonTitle = Constants.Keys.OK,
                dialogState = createDialogState,
                sourceText = null,
                onConfirmButtonClick = {
                    viewModel.addTab(it)
                }
            )
        }
    }
}


@Composable
private fun ItemLayout(
    index: Int,
    viewModel: MainViewModel,
    values: List<Tab>
) {
    val deleteTabState by lazy { mutableStateOf(false) }
    val editDialogState by lazy { mutableStateOf(false) }

    val tab = values[index]
    val lastIndex = values.count() - 1
    val nextIndex = index + 1
    val next = if (nextIndex <= lastIndex) values[nextIndex] else null
    val previousIndex = index - 1
    val previous = if (previousIndex >= 0) values[previousIndex] else null


    Card(
        shape = RoundedCornerShape(size = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                }
                .padding(vertical = 5.dp, horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(
                modifier = Modifier
                    .size(ACTION_ITEM_SIZE.dp)
                    .padding(end = 0.dp),
                onClick = {
                    viewModel.upTab(tab.id)
                    if (previous != null) {
                        viewModel.downTab(previous.id)
                    }
                },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_up),
                        tint = Color.Gray,
                        contentDescription = "up tab",
                    )
                }
            )
            IconButton(
                modifier = Modifier
                    .size(ACTION_ITEM_SIZE.dp)
                    .padding(end = 0.dp),
                onClick = {
                    viewModel.downTab(tab.id)
                    if (next != null) {
                        viewModel.upTab(next.id)
                    }
                },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_down),
                        tint = Color.Gray,
                        contentDescription = "down tab",
                    )
                }
            )

            Text(
                text = tab.name,
                fontSize = 16.sp,
                color = MaterialTheme.colors.secondary
            )

            Spacer(modifier = Modifier.width(width = 12.dp))
            IconButton(
                modifier = Modifier
                    .size(ACTION_ITEM_SIZE.dp)
                    .padding(end = 0.dp),
                onClick = {
                    editDialogState.value = true
                },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        tint = Color.Gray,
                        contentDescription = "edit action",
                    )
                }
            )

            if (index != 0) {
                IconButton(
                    modifier = Modifier
                        .size(ACTION_ITEM_SIZE.dp)
                        .padding(end = 0.dp),
                    onClick = {
                        deleteTabState.value = true
                    },
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_bin),
                            tint = Color.Gray,
                            contentDescription = "delete action",
                        )
                    }
                )
            }

            AlertDialog(
                title = "The tab with name \"" + tab.name + "\" will be deleted, you want to continue",
                confirmButtonTitle = Constants.Keys.OK,
                dialogState = deleteTabState,
                onConfirmButtonClick = {
                    viewModel.deleteTab(tab.id)
                }
            )

            Dialog(
                title = Constants.Keys.UPDATE_TAB,
                confirmButtonTitle = Constants.Keys.OK,
                dialogState = editDialogState,
                sourceText = tab.name,
                onConfirmButtonClick = {
                    viewModel.updateTab(tab.id, it)
                }
            )
        }
    }
}