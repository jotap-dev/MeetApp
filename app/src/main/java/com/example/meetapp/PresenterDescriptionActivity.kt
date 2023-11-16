package com.example.meetapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetapp.data.models.schedule.Schedule
import com.example.meetapp.databinding.ActivityPresenterDescriptionBinding
import com.example.meetapp.ui.adapters.recyclerviews.SchedulesDescriptionAdapter
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject

class PresenterDescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPresenterDescriptionBinding
    private lateinit var adapter: SchedulesDescriptionAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presenter_description)


        this.recyclerView = binding.recyclerViewPresentersDesc
        binding.recyclerViewPresentersDesc.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPresentersDesc.adapter = adapter


    }

    override fun onStart() {
        super.onStart()

        val list : MutableList<Schedule> = mutableListOf()

        binding.apply {
            presenterNameDesc.text = intent.getStringExtra("nome").toString()
            presenterCompanyDesc.text = intent.getStringExtra("empresa").toString()
            presenterDescriptionDesc.text = intent.getStringExtra("description").toString()
            val json = intent.getStringExtra("schedules")?.replace("null", "\"Bloco não informado\"")
            val jsonObj = convertToJsonArray(json.toString())

            for (i in jsonObj){

                val block = i.asJsonObject.get("bloco").asJsonObject

                val schedule = Schedule(
                    i.asJsonObject.get("id").toString().toInt(),
                    i.asJsonObject.get("local").toString(),
                    i.asJsonObject.get("descricao").toString(),
                    i.asJsonObject.get("hora_inicio").toString(),
                    i.asJsonObject.get("hora_fim").toString(),
                    i.asJsonObject.get("data_atividade").toString(),
                    i.asJsonObject.get("nome").toString(),
                    block.get("nome").toString(),
                    block.get("color").toString(),
                    "a"
                )
                list.add(schedule)
            }
        }

        adapter.setData(list)

    }

    private fun convertToJsonArray(string: String) : JsonArray {
        return Gson().fromJson(string, JsonArray::class.java)
    }
}