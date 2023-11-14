package com.example.meetapp.data.models.schedule

import com.google.gson.JsonArray

data class Schedule(
    val id: Int,
    val local: String,
    val descricao: String,
    val horarioInicio: String,
    val horarioFim: String,
    val atividadeData: String,
    val atividadeNome: String,
    val atividadeBloco: String,
    val cor: String,
    val palestrantes: String
)
