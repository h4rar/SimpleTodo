package h4rar.space.simpletodo.screens

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import h4rar.space.simpletodo.model.Note
import h4rar.space.simpletodo.ui.theme.BlackGray
import kotlin.math.roundToInt

const val ANIMATION_DURATION = 450
const val MIN_DRAG_AMOUNT = 6


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DraggableCard(
    note: Note,
    cardHeight: Dp,
    isRevealed: Boolean,
    cardOffset: Float,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    onClick: () -> Unit
) {
    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    val transition = updateTransition(transitionState, "cardTransition")
    val cardBgColor by transition.animateColor(
        label = "cardBgColorTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = {
            if (isRevealed) MaterialTheme.colors.background else MaterialTheme.colors.background
//            if (isRevealed) BlackGray else BlackGray
        }
    )
    val offsetTransition by transition.animateFloat(
        label = "cardOffsetTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isRevealed) cardOffset else 0f },

        )
    val cardElevation by transition.animateDp(
        label = "cardElevation",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isRevealed) 40.dp else 2.dp }
    )

    Card(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(cardHeight)
            .offset { IntOffset(offsetTransition.roundToInt(), 0) }
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    when {
                        delta >= MIN_DRAG_AMOUNT -> onCollapse()
                        delta < -MIN_DRAG_AMOUNT -> onExpand()
                    }
                }
            ),
        backgroundColor = cardBgColor,
        shape = remember {
            RoundedCornerShape(10.dp)
        },
        elevation = cardElevation,
        onClick = { onClick() },
        content = { CardTitle(note = note) }
    )
}