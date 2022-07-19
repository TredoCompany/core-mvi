package kz.tredo.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kz.tredo.mvi.DataState
import kz.tredo.sample.ui.theme.MVITheme

class MainActivity : ComponentActivity() {

    private val viewModel: TestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MVITheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LaunchedEffect(key1 = Unit, block = {
                        viewModel.onTriggerEvent(TestIntent.GetUsers)
                    })

                    when (val state =
                        viewModel.getUsersState.collectAsState().value) {
                        DataState.ShowLoading -> {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(32.dp)
                            )
                        }
                        is DataState.Success -> {
                            val users = state.data
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(users) { user ->
                                    Text(
                                        modifier = Modifier.padding(16.dp),
                                        text = user.name
                                    )
                                }
                            }
                        }
                        is DataState.Error -> {
                            // show error
                        }
                        else -> {
                            // do nothing
                        }
                    }
                }
            }
        }
    }
}