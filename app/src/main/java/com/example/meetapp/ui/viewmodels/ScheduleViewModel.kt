package com.example.meetapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.meetapp.data.models.schedule.Schedule
import com.example.meetapp.data.repositories.PresentersRepository
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_OK

class ScheduleViewModel constructor(private val repository: PresentersRepository) :  ViewModel(){

    val allScheduleList = MutableLiveData<List<Schedule>>()
    val errorMessages = MutableLiveData<String>()

    fun getAllSchedules(){
        val request = repository.getAllSchedules()
        request.enqueue(object : Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.code() == HTTP_OK){
                    val array = response.body()?.get("horarios")
                    val list : MutableList<Schedule> = mutableListOf()
                    if (array != null) {
                        for (i in array.asJsonArray) {
                            val item = i.toString().replace("null", "\"Bloco não informado\"")
                            val json = convertToJsonObject(item)
                            val activity = json.get("atividade").asJsonObject
                            val block =
                                if (activity.get("bloco").toString() == "Bloco não informado") "Bloco não informado"
                                else activity.get("bloco").toString()
                            val blockName =
                                if (block != "Bloco não informado") convertToJsonObject(block).get("nome").toString()
                                else block
                            val blockColor =
                                if (block != "Bloco não informado") convertToJsonObject(block).get("cor").toString()
                                else "#FF5050"

                            val schedule = Schedule(
                                json.get("id").toString().toInt(),
                                activity.get("local").toString(),
                                activity.get("descricao").toString(),
                                json.get("hora_inicio").toString(),
                                json.get("hora_fim").toString(),
                                json.get("data_atividade").toString(),
                                activity.get("nome").toString(),
                                blockName,
                                blockColor,
                                activity.get("palestrantes").toString()
                            )
                            Log.i("LISTA: " , schedule.toString())
                            list.add(schedule)
                        }
                        allScheduleList.postValue(list)
                    }
                }else{
                    errorMessages.postValue("ERRO DE CONEXÃO: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                errorMessages.postValue(t.message)
            }


        })}

    private fun convertToJsonObject(string: String) : JsonObject{
        return Gson().fromJson(string, JsonObject::class.java)
    }

}

class ScheduleViewModelFactory(private val repository: PresentersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScheduleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}