package com.example.triviaapp2.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviaapp2.R
import com.example.triviaapp2.model.QuestionItem
import com.example.triviaapp2.screens.QuestionsViewModel
import com.example.triviaapp2.util.AppColors

@Composable
fun Questions(viewModel: QuestionsViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()
    val questionIndex = remember {
        mutableStateOf(0)
    }
    if(viewModel.data.value.loading == true){
        CircularProgressIndicator()
        Log.d("Loading", "Questions: Loading.....")
    }else{
        val question = try {
            questions?.get(questionIndex.value)
        }catch (ex:Exception){
            null
        }
        if(questions != null){
            if (question != null) {
                QuestionDisplay(question, questionIndex, viewModel){
                    questionIndex.value = questionIndex.value + 1
                }
            }
        }
        }
    }





@Composable
fun QuestionDisplay(
    question: QuestionItem = QuestionItem("False", "Life", listOf("True","False"), "Does she love me?"),
    questionIndex: MutableState<Int>,
    viewModel: QuestionsViewModel,
    onNextClicked: (Int) -> Unit= {}

){
    val choicesState = remember(question){
        question.choices.toMutableList()
    }
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f,10f), 0f)
    val answerState = remember (question){
        mutableStateOf<Int?>(null)
    }
    val correctAnswerState = remember(question) {
        mutableStateOf<Boolean?>(null)
    }

    // check if selected choice is the correct answer
    val updateAnswer: (Int) -> Unit = remember(question){
        {
            answerState.value = it
            correctAnswerState.value = choicesState[it] == question.answer
        }
    }



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
            horizontalAlignment = Alignment.Start
        ){
            //progres bar
            if(questionIndex.value >= 2) ShowProgress(questionIndex.value)
            // Question counter text
            QuestionCounter(counter = questionIndex.value + 1, outOff = viewModel.totalQuestionCount() )
            // dotted line
            DottedLine(pathEffect = pathEffect)
            // Actual question text
            Column {
                Text(
                    text = question.question,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.my_white),
                    modifier = Modifier
                        .fillMaxHeight(0.3f)

                )
            }

            // radio button - choices
            choicesState.forEachIndexed { index, answerText ->
                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .height(45.dp)
                        .border(
                            width = 4.dp,
                            brush = Brush.linearGradient
                                (
                                colors = listOf(
                                    colorResource(id = R.color.my_blue),
                                    colorResource(id = R.color.my_Light_blue)
                                )
                            ),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clip(
                            RoundedCornerShape(
                                topStartPercent = 50,
                                topEndPercent = 50,
                                bottomEndPercent = 50,
                                bottomStartPercent = 50
                            )
                        )
                        .background(Color.Transparent),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    RadioButton(selected = answerState.value == index,
                        onClick = {
                            updateAnswer(index)
                        },
                        modifier = Modifier
                            .padding(start = 16.dp),
                        colors = RadioButtonDefaults.colors(selectedColor =
                        if (correctAnswerState.value == true && index == answerState.value) {
                            Color.Green
                        }else{
                            Color.Red
                        })
                        )

                    val annotatedString = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontWeight = FontWeight.Light,
                            color = if (correctAnswerState.value == true && index == answerState.value) {
                                Color.Green
                            }else if (correctAnswerState.value == false && index == answerState.value){
                                Color.Red
                            }else {
                                Color.White
                            },
                            fontSize = 17.sp
                        )){
                            append(answerText)
                        }
                    }
                    Text(
                        text = annotatedString,
                        )

                }
            }

            Button(onClick = { onNextClicked(questionIndex.value) },
                modifier = Modifier
                    .padding(15.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                shape = RoundedCornerShape(34.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.my_blue)), content = {
                    Text(text = "Next",
                        modifier = Modifier.padding(4.dp),
                        color = colorResource(id = R.color.my_white),
                        fontSize = 17.sp)

                })





        }
    }
}

//@Preview(showBackground = true)
@Composable
fun QuestionCounter(counter :Int , outOff: Int ){
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)){
            withStyle(style = SpanStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 27.sp
                )){
                append("Question $counter/")
                withStyle(style = SpanStyle(
                    color = Color.White,
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




@Composable
fun ShowProgress(score: Int) {

    val gradient = Brush.linearGradient(listOf(Color(0xFFF95075),
        Color(0xFFBE6BE5)))

    val progressFactor = remember(score) {
        mutableStateOf(score*0.005f)

    }
    Row(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .height(45.dp)
        .border(
            width = 4.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    colorResource(id = R.color.my_blue),
                    colorResource(id = R.color.my_yellow)
                )
            ),
            shape = RoundedCornerShape(34.dp)
        )
        .clip(
            RoundedCornerShape(
                topStartPercent = 50,
                topEndPercent = 50,
                bottomEndPercent = 50,
                bottomStartPercent = 50
            )
        )
        .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically) {
        Button(
            contentPadding = PaddingValues(1.dp),
            onClick = { },
            modifier = Modifier
                .fillMaxWidth(progressFactor.value)
                .background(brush = gradient),
            enabled = false,
            elevation = null,
            colors = buttonColors(
                contentColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            )) {
            Text(text = (score*10).toString(),
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(23.dp))
                    .fillMaxHeight(0.87f)
                    .fillMaxWidth()
                    .padding(6.dp),
                color = Color.White,
                textAlign = TextAlign.Center)

//
        }


    }
}

