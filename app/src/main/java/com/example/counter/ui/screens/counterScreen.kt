package com.example.counter.ui.screens


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.counter.R
import com.example.counter.data.viewmodel.CounterViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.counter.ui.state.UiState


@Composable
fun CounterScreen() {
    val viewModel = hiltViewModel<CounterViewModel>()
    val state by viewModel.state.collectAsState()


    CounterScreenContent(
        state = state,
        incrementCount = viewModel::incrementCount,
        decrementCount = viewModel::decrementCount,
        resetCount = viewModel::resetCount
    )
}

@Composable
fun CounterScreenContent(
    state: UiState,
    incrementCount: () -> Unit,
    decrementCount: () -> Unit,
    resetCount: () -> Unit
) {
    Log.d("Counter State", "$state")
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.counterTitle)) },
                backgroundColor = colorResource(id = R.color.teal_200)
            )
        },
        floatingActionButton = { FloatingResetButton(resetCount) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CounterNumber(state.counter.count)
            IncrementOrDecrement(incrementCount = incrementCount, decrementCount = decrementCount)
            Spacer(modifier = Modifier.height(20.dp))
            CounterLabel()

        }
    }

}

@Composable
fun CounterNumber(count: Int) {
    Log.d("Counter number", "$count")
    Text(
        text = count.toString(),
        fontStyle = FontStyle.Italic,
        color = colorResource(id = R.color.teal_700),
        fontSize = 150.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun IncrementOrDecrement(incrementCount: () -> Unit, decrementCount: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IncreaseCount(incrementCount)
        Spacer(modifier = Modifier.width(20.dp))
        DecreaseCount(decrementCount)
    }
}

@Composable
fun IncreaseCount(incrementCount: () -> Unit) {
    Button(
        onClick = incrementCount,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.teal_200)
        ),
    ) {
        Icon(Icons.Rounded.Add, "Add")
    }
}

@Composable
fun DecreaseCount(decrementCount: () -> Unit) {
    Button(
        onClick = decrementCount,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.teal_200)
        ),
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_round_remove_24), "Remove")
    }
}

@Composable
fun CounterLabel() {
    Text(
        text = stringResource(id = R.string.counterLabel),
        fontStyle = FontStyle.Italic,
        color = colorResource(id = R.color.teal_700),
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun FloatingResetButton(resetCount: () -> Unit) {
    ExtendedFloatingActionButton(
        icon = { Icon(Icons.Rounded.Refresh, "reset") },
        text = { Text("Reset") },
        onClick = resetCount,
        elevation = FloatingActionButtonDefaults.elevation(8.dp),
        backgroundColor = colorResource(id = R.color.teal_200)
    )
}