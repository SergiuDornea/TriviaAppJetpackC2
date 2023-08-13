package com.example.triviaapp2.repository


import android.util.Log
import com.example.triviaapp2.data.DataOrException
import com.example.triviaapp2.model.QuestionItem
import com.example.triviaapp2.network.QuestionApi
import javax.inject.Inject

// wrap the data into another class to handle exceptions (into DataOrException class )
class QuestionRepository @Inject constructor(private val api: QuestionApi){
    //boolean to check if its still loading
    private val dataOrException = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()

    suspend fun getAllQuestions() : DataOrException<ArrayList<QuestionItem>, Boolean, Exception>{
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestions()
            if(dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false

        }catch (exception: Exception){
            dataOrException.e = exception
            Log.d("Exception", "getAllQuestions: ${dataOrException.e!!.localizedMessage}")
        }
        return dataOrException
    }

}