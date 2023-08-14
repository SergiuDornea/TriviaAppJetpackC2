package com.example.triviaapp2.component

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import com.example.triviaapp2.screens.QuestionsViewModel

@Composable
fun Questions(viewModel: QuestionsViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()
    if(viewModel.data.value.loading == true){
        CircularProgressIndicator()
        Log.d("Loading", "Questions: Loading.....")
    }else{
        questions?.forEach{
            Log.d("Size", "Questions: ${it.question}")
        }
    }

}
