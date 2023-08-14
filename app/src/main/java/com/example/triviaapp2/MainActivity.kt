package com.example.triviaapp2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.triviaapp2.screens.QuestionsViewModel
import com.example.triviaapp2.ui.theme.TriviaApp2Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TriviaApp2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TriviaHome()
                }
            }
        }
    }
}



@Composable
fun TriviaHome(viewModel: QuestionsViewModel= hiltViewModel()){
    Questions(viewModel)
    
}

@Composable
fun Questions(viewModel: QuestionsViewModel ) {
    val questions = viewModel.data.value.data?.toMutableList()
    if(viewModel.data.value.loading == true){
        Log.d("Loading", "Questions: Loading.....")
    }else{
        questions?.forEach{
            Log.d("Size", "Questions: ${it.question}")
        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TriviaApp2Theme {
       
    }
}