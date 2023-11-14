package com.example.meetapp.data.models.colaborator

import com.google.gson.JsonObject

data class Colaborator(
    val id : Int,
    val ordem: Int,
    val description: String,
    val url: String,
    val imagem: String
)
