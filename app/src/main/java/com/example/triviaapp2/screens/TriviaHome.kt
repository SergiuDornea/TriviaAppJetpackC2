package com.example.triviaapp2.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.triviaapp2.component.Questions

@Composable
fun TriviaHome(viewModel: QuestionsViewModel= hiltViewModel()){
    Questions(viewModel)

}

