package h4rar.space.simpletodo.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import h4rar.space.simpletodo.model.Note

@Composable
fun CardTitle(note: Note) {
    Text(
        text = note.title,
        style = TextStyle(
            fontSize = 25.sp,
            fontFamily = FontFamily.SansSerif,
            color = if (note.isCompleted) Color.Gray else MaterialTheme.colors.secondary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight(400)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
        ,
        textAlign = TextAlign.Center,
        textDecoration =
        if (note.isCompleted)
            TextDecoration.LineThrough
        else
            TextDecoration.None
    )
}