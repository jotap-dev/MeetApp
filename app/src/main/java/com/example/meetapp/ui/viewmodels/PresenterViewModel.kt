package com.example.meetapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.meetapp.data.models.presenter.Presenter
import com.example.meetapp.data.repositories.PresentersRepository
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_OK

class PresenterViewModel constructor(private val repository: PresentersRepository) :  ViewModel(){

    val allPresentersList = MutableLiveData<List<Presenter>>()
    val errorMessages = MutableLiveData<String>()

    fun getAllPresenters(){
        val request = this.repository.getAllPresenters()
        request.enqueue(object : Callback<JsonObject>{
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (response.code() == HTTP_OK){
                    val array = response.body()?.get("palestrantes")
                    val list : MutableList<Presenter> = mutableListOf()
                    Log.i("RESPOSTA: " , array.toString())
                    if (array != null) {
                        Log.i("RESPOSTA: " , array.asJsonArray.toString())
                    }

                    if (array != null) {
                        for (i in array.asJsonArray){
                            val item = i.asJsonObject
                            val presenter = Presenter(
                                item.get("id").toString().toInt(),
                                item.get("nome").toString(),
                                item.get("empresa").toString(),
                                item.get("descricao").toString(),
                                item.get("imagem").toString(),
                                item.get("ordem").toString().toInt(),
                                item.get("atividades").asJsonArray,
                            )
                            Log.i("PRESENTER :", presenter.toString())
                            list.add(presenter)
                        }
                        Log.i("LISTA:", list.toString())
                    }

                    allPresentersList.postValue(list)

                }else{
                    errorMessages.postValue("Erro ao listar palestrantes: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                errorMessages.postValue(t.message)
            }

        })
    }

}

class PresenterViewModelFactory(private val repository: PresentersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PresenterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PresenterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}