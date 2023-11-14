package com.example.meetapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.meetapp.data.models.colaborator.Colaborator
import com.example.meetapp.data.models.notification.Notification
import com.example.meetapp.data.repositories.PresentersRepository
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class HomePageViewModel constructor(private val repository: PresentersRepository) :  ViewModel(){

    val allNotificationsList = MutableLiveData<List<Notification>>()
    val errorMessages = MutableLiveData<String>()
    var banner = MutableLiveData<String>()

    fun getAllNotifications(){
        val request = this.repository.getAllNotifications()
        request.enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK){
                    val array = response.body()?.get("notificacoes")
                    val list : MutableList<Notification> = mutableListOf()

                    if (array != null) {
                        for (i in array.asJsonArray){
                            val item = i.toString().replace("null", "\"Sem descrição informada\"")
                            val json = convertToJsonObject(item)

                            val notification = Notification(
                                json.get("id").asInt,
                                json.get("mensagem").asString,
                                json.get("titulo").asString,
                                json.get("app").asJsonObject.get("nome").toString()
                            )
                            list.add(notification)
                        }
                    }
                    if (array != null) {
                        val img = array.asJsonArray.get(0).asJsonObject.get("app").asJsonObject.get("banner").toString()
                        banner.postValue(img)
                    }

                    allNotificationsList.postValue(list)

                }else{
                    errorMessages.postValue("Erro ao listar palestrantes: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                errorMessages.postValue(t.message)
            }

        })
    }

    private fun convertToJsonObject(string: String) : JsonObject {
        return Gson().fromJson(string, JsonObject::class.java)
    }

}

class HomePageViewModelFactory(private val repository: PresentersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomePageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomePageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}