package com.example.counter.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
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



@Composable
fun CounterScreen(viewModel: CounterViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.counterTitle)) },
                backgroundColor = colorResource(id = R.color.teal_200)
            )
        },
        floatingActionButton = { FloatingResetButton(viewModel) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val count = viewModel.count.collectAsState()
            CounterNumber(count)
            IncrementOrDecrement(viewModel)
            Spacer(modifier = Modifier.height(20.dp))
            CounterLabel()

        }
    }

}

@Composable
fun CounterNumber(count : State<Int>) {
    Text(
        text = count.value.toString(),
        fontStyle = FontStyle.Italic,
        color = colorResource(id = R.color.teal_700),
        fontSize = 150.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun IncrementOrDecrement(viewModel: CounterViewModel) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IncreaseCount(viewModel)
        Spacer(modifier = Modifier.width(20.dp))
        DecreaseCount(viewModel)
    }
}

@Composable
fun IncreaseCount(viewModel: CounterViewModel) {
    Button(onClick = { viewModel.incrementCount() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.teal_200)),
    ) {
        Icon(Icons.Rounded.Add, "Add")
    }
}

@Composable
fun DecreaseCount(viewModel: CounterViewModel){
    Button(onClick = { viewModel.decrementCount() },
    colors = ButtonDefaults.buttonColors(
        backgroundColor = colorResource(id = R.color.teal_200)),
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
fun FloatingResetButton(viewModel: CounterViewModel) {
    ExtendedFloatingActionButton(
        icon = { Icon(Icons.Rounded.Refresh, "reset") },
        text = { Text("Reset") },
        onClick = { viewModel.resetCount() },
        elevation = FloatingActionButtonDefaults.elevation(8.dp),
        backgroundColor = colorResource(id = R.color.teal_200)
    )
}