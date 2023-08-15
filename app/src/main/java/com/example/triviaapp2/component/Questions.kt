package com.example.triviaapp2.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviaapp2.R
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

@Preview
@Composable
fun QuestionDisplay(){
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f,10f), 0f)
    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = colorResource(id =  R.color.my_dark_blue)
            ){
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            // Question counter text
            QuestionCounter()
            // dotted line

            DottedLine(pathEffect = pathEffect)
            // Actual questionn text







            
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun QuestionCounter(counter :Int = 10, outOff: Int = 1000){
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)){
            withStyle(style = SpanStyle(
                color = colorResource(id =  R.color.my_white),
                fontWeight = FontWeight.Bold,
                fontSize = 27.sp
                )){
                append("Question $counter/")
                withStyle(style = SpanStyle(
                    color = colorResource(id =  R.color.my_white),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )){
                    append("$outOff")
                }
            }
        }
    }
        )
}



@Composable
fun DottedLine(pathEffect: PathEffect){
    Canvas(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(2.dp)
    ){
        drawLine(
            color = Color.White,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = size.width, y = 0f),
            pathEffect = pathEffect


        )
    }
}
