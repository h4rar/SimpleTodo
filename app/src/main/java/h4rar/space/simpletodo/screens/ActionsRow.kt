package h4rar.space.simpletodo.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import h4rar.space.simpletodo.R

@Composable
fun ActionsRow(
    actionIconSize: Dp,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
) {
    Row() {
        IconButton(
            modifier = Modifier.size(actionIconSize),
            onClick = onEdit,
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    tint = Color.Gray,
                    contentDescription = "edit action",
                )
            },
        )
        IconButton(
            modifier = Modifier.size(actionIconSize),
            onClick = onDelete,
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bin),
                    tint = Color.Gray,
                    contentDescription = "delete action",
                )
            }
        )
    }
}