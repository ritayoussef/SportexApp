package com.example.sportsappteamlongfoot.model

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.common.ServerException
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.google.firebase.vertexai.*
import kotlinx.coroutines.*

//Refer to the following instructions: https://medium.com/@bhoomigadhiya/integrating-googles-gemini-into-the-android-app-520508975c2e
class AIModel{
    //Using Gemini
    val generativeModel = GenerativeModel(
    // For text-only input, use the gemini-pro model
    modelName = "gemini-1.5-flash",

    //WARNING: DO NOT USE THIS API KEY FOR ANYTHING ELSE.
    apiKey = "AIzaSyAHNhbV0vf6dV1xSTvHHxhjS0qzd1POPBc",
)

//Uses coroutines
    suspend fun GenerateAIResponse(prompt: String): String? {

        //Gets the ai response
        try{
            val response = generativeModel.generateContent(prompt)

            //returns the text of the response
            return response.text
        }
        catch(e: Exception){

            if (e is ServerException){
                return "There was an error: \n ${e.message} \n ${e.cause}"
            }

            return "There was an unknown error: \n ${e}"
        }
    }
}
