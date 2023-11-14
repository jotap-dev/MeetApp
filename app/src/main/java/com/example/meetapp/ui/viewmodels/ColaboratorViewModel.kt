package com.example.meetapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.meetapp.data.models.colaborator.Colaborator
import com.example.meetapp.data.models.presenter.Presenter
import com.example.meetapp.data.repositories.PresentersRepository
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class ColaboratorViewModel constructor(private val repository: PresentersRepository) :  ViewModel(){

    val allColaboratorList = MutableLiveData<List<Colaborator>>()
    val errorMessages = MutableLiveData<String>()

    fun getAllColaborators(){
        val request = this.repository.getAllColaborators()
        request.enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK){
                    val array = response.body()?.get("parceiros")
                    val list : MutableList<Colaborator> = mutableListOf()

                    if (array != null) {
                        for (i in array.asJsonArray){
                            val item = i.toString().replace("null", "\"Sem descrição informada\"")
                            val json = convertToJsonObject(item)
                            val colaborator = Colaborator(
                                json.get("id").asInt,
                                json.get("ordem").asInt,
                                json.get("categoria").asJsonObject.get("descricao").toString(),
                                json.get("url").asString,
                                json.get("imagem").asString
                            )
                            list.add(colaborator)
                        }
                    }

                    allColaboratorList.postValue(list)

                }else{
                    errorMessages.postValue("Erro ao listar palestrantes: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                errorMessages.postValue(t.message)
            }

        })
    }

    private fun convertToJsonObject(string: String) : JsonObject{
        return Gson().fromJson(string, JsonObject::class.java)
    }

}

class ColaboratorViewModelFactory(private val repository: PresentersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ColaboratorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ColaboratorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}