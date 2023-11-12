package com.example.meetapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.meetapp.R
import com.example.meetapp.databinding.FragmentHomePageBinding
import com.example.meetapp.databinding.FragmentScheduleBinding

class ScheduleFragment: Fragment() {

    private lateinit var binding: FragmentScheduleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater)

        return binding.root
    }

    companion object{
        fun getInstance(): ScheduleFragment{
            return ScheduleFragment()
        }
    }
}