package com.example.meetapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetapp.data.models.schedule.Schedule
import com.example.meetapp.data.repositories.PresentersRepository
import com.example.meetapp.data.services.DataEventService
import com.example.meetapp.databinding.FragmentScheduleBinding
import com.example.meetapp.ui.adapters.recyclerviews.SchedulesAdapter
import com.example.meetapp.ui.viewmodels.ScheduleViewModel
import com.example.meetapp.ui.viewmodels.ScheduleViewModelFactory

class ScheduleFragment: Fragment() {

    private lateinit var binding: FragmentScheduleBinding
    private lateinit var scheduleViewModel: ScheduleViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var schedulesAdapter: SchedulesAdapter
    private val dataEventService = DataEventService.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(inflater)

        this.schedulesAdapter = SchedulesAdapter()
        this.recyclerView = binding.recyclerViewSchedules
        recyclerView.adapter = this.schedulesAdapter
        recyclerView.layoutManager = LinearLayoutManager(view?.context)

        scheduleViewModel =  ViewModelProvider(this, ScheduleViewModelFactory(PresentersRepository(dataEventService))).get(
            ScheduleViewModel::class.java
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scheduleViewModel.allScheduleList.observe(viewLifecycleOwner, Observer { schedules ->
            binding.loadingView.visibility = View.INVISIBLE
            updateSchedulesList(schedules)
        })

        scheduleViewModel.errorMessages.observe(viewLifecycleOwner, Observer {
            //Toast.makeText(view.context, it, Toast.LENGTH_LONG).show()
            Log.i("ERRO : " , it)
            updateSchedulesList(listOf())
        })

        scheduleViewModel.allScheduleList.observe(viewLifecycleOwner) { errors ->
            errors?.let { schedulesAdapter.submitList(it) }
        }

    }

    override fun onResume() {
        super.onResume()
        scheduleViewModel.getAllSchedules()
    }

    private fun updateSchedulesList(schedules: List<Schedule>) {

        schedulesAdapter.setData(schedules)

    }

    companion object{
        fun getInstance(): ScheduleFragment{
            return ScheduleFragment()
        }
    }
}