package com.example.meetapp.data.models.presenter

import com.google.gson.JsonArray
import com.google.gson.JsonObject

data class Presenter(
    val id: Int,
    val nome: String,
    val empresa: String,
    val descricao: String,
    val imagem: String,
    val ordem: Int,
    val atividades: JsonArray
)
