package com.example.meetapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.meetapp.R
import com.example.meetapp.databinding.FragmentHomePageBinding
import com.example.meetapp.databinding.FragmentPresentersBinding

class PresentersFragment: Fragment() {

    private lateinit var binding: FragmentPresentersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPresentersBinding.inflate(inflater)

        return binding.root
    }

    companion object{
        fun getInstance(): PresentersFragment{
            return PresentersFragment()
        }
    }
}