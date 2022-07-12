package com.example.core_ui.screens


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import com.example.core.viewmodel.CounterViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.event.CounterEvent
import com.example.core.state.AltUiState
import com.example.core_ui.R


@Composable
fun CounterScreen() {
    val viewModel = hiltViewModel<CounterViewModel>()
    val state by viewModel.uiState.collectAsState()


    CounterScreenContent(
        state = state,
        eventHandler = viewModel::postEvent
    )
}

@Composable
fun CounterScreenContent(
    state: AltUiState,
    eventHandler: (CounterEvent) -> Unit
) {
    Log.d("Counter State", "$state")
    val counter = state.count
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.counterTitle)) },
                backgroundColor = colorResource(id = R.color.teal_200)
            )
        },
        floatingActionButton = { FloatingResetButton { eventHandler(CounterEvent.ResetCount(counter)) } }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when(state){
                AltUiState.Loading -> LoadingWheel()
                AltUiState.Error -> ErrorMessage(state.error)
                else -> {
                    CounterNumber(counter)
                    IncrementOrDecrement(eventHandler, counter)
                    Spacer(modifier = Modifier.height(20.dp))
                    CounterLabel()
                }
            }


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
fun IncrementOrDecrement(
    eventHandler: (CounterEvent) -> Unit,
    counter: Int
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IncreaseCount(eventHandler, counter)
        Spacer(modifier = Modifier.width(20.dp))
        DecreaseCount(eventHandler, counter)
    }
}

@Composable
fun IncreaseCount(
    eventHandler: (CounterEvent) -> Unit,
    counter: Int
) {
    Button(
        onClick = { eventHandler(CounterEvent.IncrementCount(counter)) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.teal_200)
        ),
    ) {
        Icon(Icons.Rounded.Add, "Add")
    }
}

@Composable
fun DecreaseCount(
    eventHandler: (CounterEvent) -> Unit,
    counter: Int
) {
    Button(
        onClick = { eventHandler(CounterEvent.DecrementCount(counter)) },
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

@Composable
fun LoadingWheel(){
    CircularProgressIndicator(
        color = colorResource(id = R.color.teal_700),
        modifier = Modifier.width(150.dp).height(150.dp),
        strokeWidth = 8.dp
    )
}

@Composable
fun ErrorMessage(error : String){
    Text(
        text = error,
        fontStyle = FontStyle.Italic,
        color = colorResource(id = R.color.teal_700),
        fontSize = 100.sp,
        fontWeight = FontWeight.Bold,
    )
}